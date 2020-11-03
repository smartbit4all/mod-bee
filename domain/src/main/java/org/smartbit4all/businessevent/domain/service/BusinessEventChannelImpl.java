package org.smartbit4all.businessevent.domain.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartbit4all.businessevent.domain.entity.ActiveEventDef;
import org.smartbit4all.businessevent.domain.entity.EventBinaryContentDef;
import org.smartbit4all.businessevent.domain.entity.EventBodyDef;
import org.smartbit4all.businessevent.domain.entity.EventChannelDef;
import org.smartbit4all.businessevent.domain.entity.EventProcessLogDef;
import org.smartbit4all.domain.application.TimeManagementService;
import org.smartbit4all.domain.data.DataRow;
import org.smartbit4all.domain.data.TableData;
import org.smartbit4all.domain.data.TableDatas;
import org.smartbit4all.domain.data.TableDatas.BuilderWithFlexibleProperties;
import org.smartbit4all.domain.meta.EntityService;
import org.smartbit4all.domain.service.query.Query;
import org.smartbit4all.domain.utility.crud.Crud;
import org.smartbit4all.types.binarydata.BinaryData;
import org.smartbit4all.types.binarydata.BinaryDataOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.MimeTypeUtils;

/**
 * The channel is the instance of the named channel that contains the execution policy and all the
 * parameters for the given channel. The name of the channel is unique and identify it globally. The
 * event channel has a smart life cycle and they ensure that all the events will be processed one
 * and only one time. This module uses database tables to store the events and their state.
 * 
 * @author Peter Boros
 */
public class BusinessEventChannelImpl implements BusinessEventChannel, BusinessEventChannelService {

  private static final Logger log = LoggerFactory.getLogger(BusinessEventChannelImpl.class);

  private static final RuntimeException reSyncFailed =
      new RuntimeException("Failed to process because of server shutdown.");

  /**
   * The this as the interface Proxy! Use this instead of calling the methods directly.
   */
  private BusinessEventChannelService self;

  @Override
  public void setSelf(BusinessEventChannelService self) {
    this.self = self;
  }

  /**
   * The operation mode of the given channel.
   */
  private ChannelOperationMode mode = ChannelOperationMode.ALL_NODE_MAX_THROUGHPUT;

  /**
   * The state of the given channel.
   */
  private ChannelState state = ChannelState.INACTIVE;

  /**
   * @see ChannelOperationMode
   * @return
   */
  @Override
  public ChannelOperationMode getMode() {
    return mode;
  }

  /**
   * @see ChannelOperationMode
   * @param mode
   */
  @Override
  public void setMode(ChannelOperationMode mode) {
    this.mode = mode;
  }

  /**
   * {@link ChannelState}
   * 
   * @return
   */
  @Override
  public ChannelState getState() {
    return state;
  }

  /**
   * If an event is a function call itself then the channel will run the function. But if it's
   * simple event then the generic process service will do the job.
   */
  private BusinessEventProcessService processService;

  /**
   * If the given channel is stopping and releasing the items for other runtime then this count down
   * latch ensure that all the threads will executed before the state is modified..
   */
  private CountDownLatch shutdownInProgress = null;

  /**
   * The events to release at the end of the shutdown process. These events haven't been processed
   * until the shutdown start.
   */
  private BlockingQueue<Long> eventsToRelease;

  /**
   * The database code of the channel. The save and the read use this in the database functions.
   */
  private String channelCode;

  /**
   * The thread pool executor for the given channel and provides the execution threads for the
   * channels. For {@link ChannelType#ASYNCHRONOUS} type.
   */
  private ExecutorService executorService;

  /**
   * In case of scheduled execution ({@link ChannelType#SCHEDULED}).
   */
  private ScheduledExecutorService scheduledExecutorService;

  /**
   * The type of the channel.
   * 
   * @see ChannelType
   */
  private ChannelType type = ChannelType.SYNCHRONOUS;

  /**
   * The number of threads to allocate in the executor service. The default is 2 so there is a basic
   * multi-threaded behavior.
   */
  private int executionThreadLimit = 2;

  /**
   * The default value for the max queue size. There is no need to use 2 power number.
   */
  private static final int MAXEVENTQUEUESIZE_DEFAULT = 4096;

  /**
   * The is a rate. If the queue is 50% full by default then we skip execution by the post and start
   * saving the event into the database. So if the process is slowing down or we have extremely huge
   * number of incoming event then we start working from database. It's slower a little bit but we
   * would like to avoid {@link OutOfMemoryError} exception at the end.
   * 
   * It's also used to define the allocationLimit. If we have less event in the queue then we grab
   * some from the database.
   */
  private static final float EVENTQUEUESIZE_NORMAL_OPEARTION_LIMIT = 0.5f;

  /**
   * In case of {@link ChannelOperationMode#FIFO} this is the limit for grabbing events from the
   * database.
   * 
   * It's also used to define the allocationLimit. If we have less event in the queue then we grab
   * some from the database.
   */
  private static final float QUEUESIZE_LIMIT_RATE_FIFO = 0.9f;

  /**
   * The maximum size of the event queue. The maximum number of events managed in the memory of the
   * event channel.
   */
  private int maxEventQueueSize = MAXEVENTQUEUESIZE_DEFAULT;

  /**
   * If we reach this number of events in the queue then the channel will accept the events but save
   * it only into the database. Later on some node will grab this and start the execution but not
   * directly.
   */
  private int normalOperationLimit;

  /**
   * This limit will determine the number of events to allocate from the database as new event to
   * process. By default no event will be grabbed! Must be set explicitly.
   */
  // @Value("${***}")
  private int allocationLimit = 25;

  /**
   * This map contains all the events that is managed by the current node. If a node is working on
   * an event then it can wait for an execution thread or it can be under execution. In both cases
   * we will find them in this map by the unique database identifier as a key. In this way we can
   * double check the operation of the event channel. There is no way to put in the same event
   * twice.
   */
  private final Map<Long, BusinessEventData> activeEvents = new HashMap<>();

  /**
   * This controls the {@link #activeEvents}.
   */
  private final Lock lActiveEvents = new ReentrantLock();

  @Autowired
  EventBodyDef eventBody;

  @Autowired
  ActiveEventDef activeEvent;

  @Autowired
  EventProcessLogDef processLog;

  @Autowired
  BusinessEventIdService identifierService;

  @Autowired
  ApplicationRuntimeService runtimeService;

  @Autowired
  EventChannelDef eventChannel;

  @Autowired
  EventBinaryContentDef eventBinaryContent;

  @Autowired
  TimeManagementService timeService;

  @Override
  public PostEvent post() {
    PostEventImpl impl = new PostEventImpl(self, timeService);
    return impl;
  }

  Long getNextEventId() throws Exception {
    return identifierService.getNextId();
  }

  @Override
  public void start() {
    // If we execute asynchronously then we need the scheduler and the thread pool
    if (type == ChannelType.ASYNCHRONOUS) {
      executorService =
          new ThreadPoolExecutor(executionThreadLimit, executionThreadLimit, 1, TimeUnit.MINUTES,
              new LinkedBlockingQueue<Runnable>());
      scheduledExecutorService = new ScheduledThreadPoolExecutor(executionThreadLimit);
    }
  }

  @Override
  public void stop() {
    executorService.shutdownNow();
    List<Runnable> list = scheduledExecutorService.shutdownNow();
  }

  /**
   * Save the event into the active event store. TODO Transact together with the current transaction
   * but start the execution itself by the successful commit!
   * 
   * @param event The event data that contains all the information we have about the event.
   * @param state The state can define if we have an event to process later on by grabbing new event
   *        mechanism. This is the {@link BusinessEventStateEnum#W} - waiting state. But if the
   *        given node starts the execution immediately after the transaction then the state must be
   *        {@link BusinessEventStateEnum#E} - executing.
   * @return
   * @throws Exception
   */
  @Override
  @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)
  public BusinessEventState saveNewEvent(BusinessEventData event, BusinessEventStateEnum state,
      LocalDateTime nextProcessTime)
      throws Exception {
    UUID identifier = UUID.randomUUID();

    LocalDateTime now = timeService.getSystemTime();
    
    // if nextProcessTime is not specified, start processing now
    if (nextProcessTime == null) {
      nextProcessTime = now;
    }
    
    // Event creation time can be parametrized, but by default, it is the eventbody creation time
    LocalDateTime createdAt = event.createdAt;
    if(createdAt == null) {
      createdAt = now;
    }
    
    // The event data is always created it doesn't matter what is the state.
    Long bodyId = getNextEventId();
    TableData<EventBodyDef> tdBody = TableDatas.builder(eventBody).addRow()
        .set(eventBody.eventBodyId(), bodyId)
        .set(eventBody.identifier(), identifier.toString())
        .set(eventBody.actionCode(), event.actionCode)
        .set(eventBody.businessEntity(), event.businessEntity)
        .set(eventBody.businessEntityRef(), event.businessEntityRef)
        .set(eventBody.createdAt(), createdAt)
        .set(eventBody.eventChannel(), getChannelCode())
        .set(eventBody.extensionText(), event.extensionText)
        .setNotNull(eventBody.requestId(),
            event.request != null ? createBinaryContent(event.request) : null)
        .set(eventBody.sessionId(), event.sessionId).build();

    eventBody.services().crud().create().values(tdBody).execute();

    // The rest of the algorithm is highly depending on the state of the new record and the next
    // process time. The potential operations to do is managed by the builders. If we have any row
    // at the and of the function then we execute it.
    BuilderWithFlexibleProperties<ActiveEventDef> builderActiveEvent =
        TableDatas.builder(activeEvent);
    BuilderWithFlexibleProperties<EventProcessLogDef> builderProcessLog =
        TableDatas.builder(processLog);
    BuilderWithFlexibleProperties<EventBodyDef> builderEventBody =
        TableDatas.builder(eventBody);

    Long processLogId = null;
    if (state == BusinessEventStateEnum.S || state == BusinessEventStateEnum.F
        || state == BusinessEventStateEnum.W || state == BusinessEventStateEnum.E) {
      // If need to create process log.
      processLogId = getNextEventId();
      builderProcessLog.addRow()
          .set(processLog.eventbodyId(), bodyId)
          .set(processLog.eventProcessLogId(), processLogId)
          .set(processLog.expectedAllocationTime(), nextProcessTime) // We need a more sophisticated
                                                                     // decision here!
          .set(processLog.allocationTime(), now)
          .set(processLog.applicationruntimeId(), getRuntimeId())
          .set(processLog.orderNo(), 1l)
          .set(processLog.state(), state.name());
      // We need to update the event body to set the process log.
      builderEventBody.addRow()
          .set(eventBody.eventBodyId(), bodyId)
          .set(eventBody.lastProcessLogId(), processLogId);
      if (state == BusinessEventStateEnum.S || state == BusinessEventStateEnum.F) {
        // The state is finished state so we set the result of the process also
        // TODO The execution itself is also some parameter.
        builderProcessLog
            .set(processLog.processStartTime(), now)
            .set(processLog.processFinishTime(), now)
            .set(processLog.resultCode(), state == BusinessEventStateEnum.S ? "OK" : "Failed");
      } else if (state == BusinessEventStateEnum.E) {
        // This is the start time also
        builderProcessLog
            .set(processLog.processStartTime(), now);
      }
    }


    if (state == BusinessEventStateEnum.A || state == BusinessEventStateEnum.W
        || state == BusinessEventStateEnum.E) {
      // If we need to process the event later on then we need the active event record.
      builderActiveEvent.addRow()
          .set(activeEvent.activeEventId(), bodyId)
          .set(activeEvent.eventbodyId(), bodyId)
          .set(activeEvent.applicationruntimeId(),
              state != BusinessEventStateEnum.A ? getRuntimeId() : null)
          .set(activeEvent.nextProcessTime(), nextProcessTime)
          .set(activeEvent.eventChannel(), getChannelCode())
          .set(activeEvent.state(), state.name());
    }


    // Create the process log if any.
    if (!builderProcessLog.isEmpty()) {
      processLog.services().crud().create().values(builderProcessLog.build()).execute();
    }

    // The next can be the event body update. It will update the last process log value to an
    // existing record.
    if (!builderEventBody.isEmpty()) {
      eventBody.services().crud().update().values(builderEventBody.build()).execute();
    }

    // The next can be the event body update. It will update the last process log value to an
    // existing record.
    if (!builderActiveEvent.isEmpty()) {
      activeEvent.services().crud().create().values(builderActiveEvent.build()).execute();
    }

    BusinessEventState result = new BusinessEventState();
    result.identifier = identifier;
    result.dbId = bodyId;
    result.state = state;
    result.nextProcessTime = nextProcessTime;
    result.previousExecutions = 0l;
    result.processId = processLogId;
    result.eventData = event;

    if (getType() == ChannelType.ASYNCHRONOUS) {
      BusinessEventState eventStateCopy = BusinessEventState.of(result);
      TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {

        @Override
        public void afterCommit() {
          processSchedule(event, eventStateCopy);
        }
      });
    }

    return result;
  }

  @Override
  public BusinessEventState saveEventProcessReschedule(BusinessEventData event,
      BusinessEventState currentState,
      BusinessEventSchedule schedule) throws Exception {
    removeActiveEvent(currentState.dbId);
    // We need the active event record to store the fact of execution.
    BuilderWithFlexibleProperties<ActiveEventDef> builderActiveEvent =
        TableDatas.builder(activeEvent);
    // We need to add a new process log entry to initiate planned process itself.
    BuilderWithFlexibleProperties<EventProcessLogDef> builderProcessLog =
        TableDatas.builder(processLog);
    // We need to update the event itself to increment the number of processing and to set the new
    // process log record as the current one.
    BuilderWithFlexibleProperties<EventBodyDef> builderEventBody =
        TableDatas.builder(eventBody);

    LocalDateTime now = timeService.getSystemTime();
    LocalDateTime nextProcessTime = schedule.getNextProcessTime(now);


    Long processLogId = getNextEventId();
    currentState.previousExecutions++;
    currentState.processId = processLogId;
    currentState.nextProcessTime = nextProcessTime;

    // We should decide if we are already allocate the given event.
    LocalDateTime allocationTime;
    LocalDateTime processStartTime = null;
    BusinessEventStateEnum state;
    if (getType() == ChannelType.SYNCHRONOUS) {
      // If we are synchronous channel or we have to execute the event immediately then we can set
      // the allocation time and the state to waiting for execution.
      allocationTime = now;
      state = BusinessEventStateEnum.E;
      // In this case we have a synchronous channel. We set the process start time to the next
      // process time. If it's later than now then we will sleep a little bit before the real
      // execution.
      processStartTime = nextProcessTime;
    } else if (!nextProcessTime.isAfter(now)) {
      // We set the waiting for execution as a state.
      allocationTime = now;
      state = BusinessEventStateEnum.W;
    } else {
      // We don't set the allocation time but we set the state to waiting for allocation.
      allocationTime = null;
      state = BusinessEventStateEnum.A;
    }
    // Figure out if we must set the application runtime or not.
    Long appRunTime = state != BusinessEventStateEnum.A ? getRuntimeId() : null;
    currentState.state = state;

    if (currentState.state != BusinessEventStateEnum.A) {
      builderProcessLog.addRow()
          .set(processLog.eventbodyId(), currentState.dbId)
          .set(processLog.eventProcessLogId(), processLogId)
          .set(processLog.expectedAllocationTime(), nextProcessTime) // We need a more sophisticated
                                                                     // decision here!
          .set(processLog.allocationTime(), allocationTime)
          .set(processLog.applicationruntimeId(),
              appRunTime)
          .set(processLog.orderNo(), currentState.previousExecutions + 1)
          .set(processLog.state(), state.name())
          .set(processLog.processStartTime(), processStartTime);

      // We need to update the event body to set the process log.
      builderEventBody.addRow()
          .set(eventBody.eventBodyId(), currentState.dbId)
          .set(eventBody.lastProcessLogId(), processLogId);
    }


    // If we need to process the event later on then we need the active event record.
    builderActiveEvent.addRow()
        .set(activeEvent.activeEventId(), currentState.dbId)
        .set(activeEvent.eventbodyId(), currentState.dbId)
        .set(activeEvent.applicationruntimeId(),
            appRunTime)
        .set(activeEvent.nextProcessTime(), nextProcessTime)
        .set(activeEvent.eventChannel(), getChannelCode())
        .set(activeEvent.state(), state.name());


    // Create the process log if any.
    if (!builderProcessLog.isEmpty()) {
      processLog.services().crud().create().values(builderProcessLog.build()).execute();
    }

    // The next can be the event body update. It will update the last process log value to an
    // existing record.
    if (!builderEventBody.isEmpty()) {
      eventBody.services().crud().update().values(builderEventBody.build()).execute();
    }

    // The next can be the event body update. It will update the last process log value to an
    // existing record.
    if (!builderActiveEvent.isEmpty()) {
      activeEvent.services().crud().update().values(builderActiveEvent.build()).execute();
    }

    // The current event is rescheduled to execute again. Therefore the saveProcessFinish must not
    // delete the active event.
    currentState.rescheduled = true;

    return currentState;
  }

  /**
   * We modify the eventStates to make it up to date.
   */
  @Override
  @Transactional(isolation = Isolation.READ_COMMITTED)
  public void saveEventsAllocated(List<BusinessEventState> eventStates) throws Exception {

    BuilderWithFlexibleProperties<EventProcessLogDef> builderLog = TableDatas.builder(processLog);
    BuilderWithFlexibleProperties<ActiveEventDef> builderActiveEvent =
        TableDatas.builder(activeEvent);
    BuilderWithFlexibleProperties<EventBodyDef> builderEventBody =
        TableDatas.builder(eventBody);
    LocalDateTime now = timeService.getSystemTime();
    for (BusinessEventState businessEventState : eventStates) {
      Long nextEventId = getNextEventId();
      businessEventState.processId = nextEventId;
      businessEventState.previousExecutions++;
      businessEventState.state = BusinessEventStateEnum.W;
      builderLog.addRow()
          .set(processLog.eventbodyId(), businessEventState.dbId)
          .set(processLog.eventProcessLogId(), nextEventId)
          .set(processLog.expectedAllocationTime(), businessEventState.nextProcessTime)
          .set(processLog.allocationTime(), now)
          .set(processLog.processStartTime(), null)
          .set(processLog.processFinishTime(), null)
          .set(processLog.applicationruntimeId(), getRuntimeId())
          .set(processLog.orderNo(), businessEventState.previousExecutions)
          .set(processLog.resultCode(), null)
          .set(processLog.state(), businessEventState.state.name());

      builderActiveEvent.addRow()
          .set(activeEvent.activeEventId(), businessEventState.dbId)
          .set(activeEvent.applicationruntimeId(), getRuntimeId())
          .set(activeEvent.state(), businessEventState.state.name());

      builderEventBody.addRow()
          .set(eventBody.eventBodyId(), businessEventState.dbId)
          .set(eventBody.lastProcessLogId(), nextEventId);
    }

    processLog.services().crud().create().values(builderLog.build()).execute();

    activeEvent.services().crud().update().values(builderActiveEvent.build()).execute();

    eventBody.services().crud().update().values(builderEventBody.build()).execute();

  }

  @Override
  @Transactional(isolation = Isolation.READ_COMMITTED)
  public void saveEventSyncProcessFailed(List<BusinessEventState> failedStates) throws Exception {
    LocalDateTime now = timeService.getSystemTime();
    BuilderWithFlexibleProperties<EventProcessLogDef> processLogBuilder =
        TableDatas.builder(processLog);

    BuilderWithFlexibleProperties<ActiveEventDef> activeEventBuilder =
        TableDatas.builder(activeEvent);

    // We create one exception result for all the failed process log records.
    Long resultId = saveEventException(reSyncFailed);

    for (BusinessEventState eventState : failedStates) {
      processLogBuilder.addRow()
          .set(processLog.processFinishTime(), now)
          .set(processLog.resultCode(), "Failed")
          .set(processLog.state(), BusinessEventStateEnum.F.name())
          .set(processLog.resultId(), resultId)
          .set(processLog.eventProcessLogId(), eventState.processId);
      activeEventBuilder.addRow().set(activeEvent.activeEventId(), eventState.dbId);
    }

    processLog.services().crud().update().values(processLogBuilder.build()).execute();
    activeEvent.services().crud().delete().by(activeEventBuilder.build()).execute();

  }

  /**
   * Save the result of the event process. It can be successful or failed. This function can be
   * called in the {@link #executeProcessEvent(BusinessEventData, BusinessEventState)} to save the
   * result even if it's successful or failed.
   * 
   * @param event The event itself.
   * @param currentState The current state of the event. Contains the database identifier.
   * @param ex If an exception occurred while processing the event. In this case the result depends
   *        on the value of the nextProcessTime
   * @param nextProcessTime The process event instance that was the function of the execution..
   * @return
   * @throws Exception
   */
  @Override
  @Transactional(isolation = Isolation.READ_COMMITTED)
  public BusinessEventState saveEventProcessFinished(BusinessEventState currentState,
      Exception ex,
      ProcessEvent processEvent)
      throws Exception {
    LocalDateTime now = timeService.getSystemTime();

    BusinessEventStateEnum nextState =
        ex == null ? BusinessEventStateEnum.S : BusinessEventStateEnum.F;

    currentState.state = nextState;

    Long resultId = null;
    if (ex != null) {
      resultId = saveEventException(ex);
    } else if (currentState.result != null) {
      resultId = createBinaryContent(currentState.result);
    }

    // The previous execution must be finished even if the process failed or we need to reschedule.
    processLog.services().crud().update()
        .values(TableDatas.builder(processLog).addRow()
            .set(processLog.processFinishTime(), now)
            .set(processLog.resultCode(), ex == null ? "OK" : getExceptionResult(ex))
            .set(processLog.state(), nextState.name())
            .setNotNull(processLog.resultId(), resultId)
            .set(processLog.eventProcessLogId(), currentState.processId).build())
        .execute();

    // Before running the post section that could contains reschedule we must set the rescheduled to
    // false.
    currentState.rescheduled = false;

    // Now we execute the post section that was added to the process event itself. It's typically
    // the reschedule or posting of new events.
    if (processEvent.getPostSection() != null) {
      processEvent.getPostSection().execute();
    }

    // We must take a look at the state. If the post section used the active event for rescheduling
    // for example. In these cases we let the active event record. Else we need to delete it.
    if (!currentState.rescheduled) {
      activeEvent.services().crud().delete().by(TableDatas.builder(activeEvent).addRow()
          .set(activeEvent.activeEventId(), currentState.dbId)
          .build()).execute();
    }

    return currentState;
  }


  private String getExceptionResult(Throwable throwable) {
    if (throwable.getCause() != null) {
      return getExceptionResult(throwable.getCause());
    }
    return throwable.getMessage();
  }

  protected Long saveEventException(Exception ex) throws Exception {
    BinaryData data = marshallException(ex);
    return createBinaryContent(data);
  }

  protected BinaryData marshallException(Exception ex) throws Exception, IOException {
    BinaryDataOutputStream os = new BinaryDataOutputStream(0, null);
    PrintWriter s = new PrintWriter(os);
    ex.printStackTrace(s);
    s.close();
    os.close();
    BinaryData data = os.data();
    data.setMimeType(MimeTypeUtils.TEXT_PLAIN_VALUE);
    return data;
  }

  protected Long createBinaryContent(BinaryData data) throws Exception {
    Long id = identifierService.getNextId();
    TableData<EventBinaryContentDef> newBinaryContent =
        TableDatas.builder(eventBinaryContent).addRow()
            .set(eventBinaryContent.eventBinaryContentId(), id)
            .set(eventBinaryContent.lobData(), data)
            .set(eventBinaryContent.mimeType(), data.mimeType())
            .build();

    eventBinaryContent.services().crud().create().values(newBinaryContent).execute();
    return id;
  }

  /**
   * Save the fact that we start the processing of the given event. It's only an update on the state
   * of the process log and the active event. At the same time we set the process start time for the
   * process log. The start time will be the current time.
   * 
   * @param event The event itself.
   * @return
   * @throws Exception
   */
  @Override
  @Transactional(isolation = Isolation.READ_COMMITTED)
  public BusinessEventState saveEventProcessStarted(BusinessEventState currentState)
      throws Exception {

    LocalDateTime now = timeService.getSystemTime();

    processLog.services().crud().update()
        .values(TableDatas.builder(processLog).addRow()
            .set(processLog.processStartTime(), now)
            .set(processLog.state(), BusinessEventStateEnum.E.name())
            .set(processLog.eventProcessLogId(), currentState.processId).build())
        .execute();

    activeEvent.services().crud().update()
        .values(TableDatas.builder(activeEvent).addRow()
            .set(activeEvent.activeEventId(), currentState.dbId)
            .set(activeEvent.state(), BusinessEventStateEnum.E.name()).build())
        .execute();

    // Set the current state.
    currentState.state = BusinessEventStateEnum.E;

    return currentState;
  }

  @Override
  public void registerScheduledFunction(ScheduledFunction<?, ?> scheduledFunction)
      throws Exception {
    // First we must find the given record and lock it if we already have.
    TableData<EventBodyDef> tdEventBody = Crud.read(eventBody).select(eventBody.eventBodyId())
        .where(eventBody.identifier().eq(scheduledFunction.getClass().getName())).lock()
        .executeIntoTableData();
    if (tdEventBody.isEmpty()) {
      // In this case we have to post the given event. The kind of the event will ensure the
      // rescheduling always.

    }
  }

  /**
   * The execution of the event processing in the current thread.
   * 
   * @throws Exception
   */
  @Override
  public void processSync(BusinessEventData event, BusinessEventState state) throws Exception {
    // TODO We don't have a real thread for the execution but anyway we must limit the number of
    // executions. There must be some monitor here.
    addActiveEvent(state.dbId, event);
    try {
      executeProcessEvent(event, state);
    } finally {
      removeActiveEvent(state.dbId);
    }
  }

  /**
   * Execute the event using the thread infrastructure of the current channel.
   */
  @Override
  public void processAsync(BusinessEventData event, BusinessEventState state) {
    // Now we start the execution immediately! It must be after the successful execution of the
    // current transaction.
    addActiveEvent(state.dbId, event);
    executorService.submit(new Runnable() {

      @Override
      public void run() {
        try {
          executeProcessEvent(event, state);
        } catch (Exception e) {
          log.warn("Exception occured while executing the " + event + " event.", e);
        } finally {
          removeActiveEvent(state.dbId);
        }
      }
    });
  }

  @Override
  public void processSchedule(BusinessEventData event, BusinessEventState startingState) {
    // Calculate the scheduling delay by the next process time.
    LocalDateTime now = timeService.getSystemTime();
    long delay;
    if (event.nextProcessTime == null || now.isAfter(event.nextProcessTime)) {
      // The scheduling delay is 0. We can start immediately.
      delay = 0;
    } else {
      delay = ChronoUnit.MILLIS.between(now, event.nextProcessTime);
    }
    if (delay > 100) {
      scheduledExecutorService.schedule(new Runnable() {

        @Override
        public void run() {
          try {
            self.processAsync(event, startingState);
          } catch (Exception e) {
            // TODO Make proper decision.
            e.printStackTrace();
          }
        }
      }, delay, TimeUnit.MILLISECONDS);
    } else {
      self.processAsync(event, startingState);
    }
  }

  private final void executeProcessEvent(BusinessEventData event,
      BusinessEventState startingState) throws Exception {
    BusinessEventState eventProcessStartedState = startingState;
    ProcessEvent processEvent = null;
    try {
      eventProcessStartedState = self.saveEventProcessStarted(startingState);
      processEvent = processService.process().event(event);
      processEvent.channel(self);
      processEvent.eventState(eventProcessStartedState);
      processEvent.execute();

      // If the event has Bindary Data result, then use it in the BusinessEventState
      eventProcessStartedState.result = processEvent.output();
      // If we successfully processed the event then we save the result.
      try {
        self.saveEventProcessFinished(eventProcessStartedState, null, processEvent);
      } catch (Exception ex) {
        // TODO Unable to save the result of the execution! The given channel must stop to avoid
        // duplications!!
        log.error("Unable to save the result of the " + event + " execution on the " + this
            + "channel.", ex);
      }
    } catch (Exception e) {
      try {
        self.saveEventProcessFinished(eventProcessStartedState, e, processEvent);
      } catch (Exception ex) {
        // TODO Unable to save the result of the execution! The given channel must stop to avoid
        // duplications!!
        log.error("Unable to save the result of the " + event + " execution on the " + this
            + "channel.", ex);
      }
      throw e;
    }
  }

  /**
   * The service that provide the {@link ProcessEvent} function for the channel.
   * 
   * @return
   */
  @Override
  public BusinessEventProcessService getProcessService() {
    return processService;
  }

  /**
   * The service that provide the {@link ProcessEvent} function for the channel.
   * 
   * @param processService
   */
  @Override
  public void setProcessService(BusinessEventProcessService processService) {
    this.processService = processService;
  }

  @Override
  public String getChannelCode() {
    return channelCode;
  }

  @Override
  public void setChannelCode(String channelCode) {
    this.channelCode = channelCode;
  }

  @Override
  public int getExecutionThreadLimit() {
    return executionThreadLimit;
  }

  @Override
  public void setExecutionThreadLimit(int executionThreadLimit) {
    this.executionThreadLimit = executionThreadLimit;
  }

  @Override
  public int getMaxEventQueueSize() {
    return maxEventQueueSize;
  }

  @Override
  public void setMaxEventQueueSize(int maxEventQueueSize) {
    this.maxEventQueueSize = maxEventQueueSize;
  }

  @Override
  public int getNormalOperationLimit() {
    return normalOperationLimit;
  }

  @Override
  public void setNormalOperationLimit(int normalOperationLimit) {
    this.normalOperationLimit = normalOperationLimit;
  }

  @Override
  public int getAllocationLimit() {
    return allocationLimit;
  }

  @Override
  public void setAllocationLimit(int allocationLimit) {
    this.allocationLimit = allocationLimit;
  }

  @Override
  public Long getRuntimeId() {
    return runtimeService.getAppRuntimeId();
  }

  @Override
  public ChannelType getType() {
    return type;
  }

  @Override
  public void setType(ChannelType type) {
    this.type = type;
  }

  @Override
  @Scheduled(fixedDelay = 5000)
  @Transactional(isolation = Isolation.READ_COMMITTED)
  public void maintain() {
    // For every channel we collect the active events that are ready to process. The runtime is not
    // working any more or missing and we reached the next process time within a range.

    // The channel must exist to enable locking the channel with selecting for the record for
    // update.
    // TODO This is not nice! We should type the services to have the correct type service instance!
    @SuppressWarnings("unchecked")
    EntityService<EventChannelDef> services =
        eventChannel.services();
    try {
      services.lockOrCreateAndLock(eventChannel.code(), channelCode,
          BusinessEventIdService.SEQUENCE_NAME,
          null);
    } catch (Exception e1) {
      // We failed to create or lock the record of the channel.
      log.error("Unable to put lock on the " + channelCode + " event channel. Skip maintain!", e1);
      return;
    }

    // TODO Calculate more accurately, use TimeManagementService and the parameter for the app
    // runtime.
    LocalDateTime now = timeService.getSystemTime();
    LocalDateTime aliveLimitTime = now.minusSeconds(20);
    LocalDateTime lookAheadLimit = now.plusMinutes(1);

    TableData<ActiveEventDef> result = new TableData<>(activeEvent);

    Query query =
        activeEvent.services().crud().query().all().select(activeEvent.eventbody().allProperties())
            .select(activeEvent.eventbody().request().lobData())
            .where(activeEvent.applicationruntime().lastTouchTime().lt(aliveLimitTime)
                .OR(activeEvent.state().eq(BusinessEventStateEnum.A.name())).BRACKET()
                .AND(activeEvent.nextProcessTime().lt(lookAheadLimit))
                .AND(activeEvent.eventChannel().eq(channelCode)))
            .order(activeEvent.nextProcessTime().asc())
            .limit(allocationLimit).into(result);

    try {
      query.execute();

      // The business events about to start execution
      List<BusinessEventState> startedEventStates = new ArrayList<>();
      List<BusinessEventState> failedSyncEventStates = new ArrayList<>();

      for (DataRow row : result.rows()) {
        // TODO RuleBasedConversion
        BusinessEventState eventState = domain2BusinessEventState(row, true);
        eventState.processId = row.get(activeEvent.eventbody().lastProcessLogId());
        // The action depends on the type of the channel.
        switch (type) {
          case SYNCHRONOUS:
            // Save the failed execution. We assume that the process failed because of the shutdown
            // of the server. We won't process the message again.
            failedSyncEventStates.add(eventState);
            break;
          case ASYNCHRONOUS:
            // Drop into the execution.
            eventState.previousExecutions++;
            startedEventStates.add(eventState);
            break;

          default:
            break;
        }
      }

      // In one transaction it will save the failure for the sync channel events.
      if (!failedSyncEventStates.isEmpty()) {
        self.saveEventSyncProcessFailed(failedSyncEventStates);
      }
      // Start the execution if we have any event to deal with.
      if (!startedEventStates.isEmpty()) {
        self.saveEventsAllocated(startedEventStates);
        for (BusinessEventState eventState : startedEventStates) {
          processSchedule(eventState.eventData, eventState);
        }
      }

    } catch (Exception e) {
      // The exception handling could lead to retry.
      log.error("Unable to acquire lock on the records in the channel", e);
    }

  }

  private BusinessEventState domain2BusinessEventState(DataRow row, boolean withData) {
    BusinessEventState result = new BusinessEventState();
    result.dbId = row.get(activeEvent.activeEventId());
    result.identifier = UUID.fromString(row.get(activeEvent.eventbody().identifier()));
    result.state = BusinessEventStateEnum.valueOf(row.get(activeEvent.state()));
    result.eventData = domain2BusinessEventData(row);
    result.nextProcessTime = row.get(activeEvent.nextProcessTime());
    return result;
  }

  private BusinessEventData domain2BusinessEventData(DataRow row) {
    BusinessEventData result = new BusinessEventData();
    result.actionCode = row.get(activeEvent.eventbody().actionCode());
    result.businessEntity = row.get(activeEvent.eventbody().businessEntity());
    result.businessEntityRef = row.get(activeEvent.eventbody().businessEntityRef());
    result.channel = row.get(activeEvent.eventbody().eventChannel());
    result.extensionText = row.get(activeEvent.eventbody().extensionText());
    result.nextProcessTime = row.get(activeEvent.nextProcessTime());
    result.sessionId = row.get(activeEvent.eventbody().sessionId());
    result.request = row.get(activeEvent.eventbody().request().lobData());
    return result;
  }

  private final void addActiveEvent(Long id, BusinessEventData eventData) {
    lActiveEvents.lock();
    try {
      BusinessEventData prev = activeEvents.put(id, eventData);
      if (prev != null) {
        // In this case we already have the given event in the map. We put it back and throws an
        // exception.
        activeEvents.put(id, prev);
        throw new IllegalStateException(
            "The " + id + " event is already managed by the " + channelCode + " channel");
      }
    } finally {
      lActiveEvents.unlock();
    }
  }

  final void removeActiveEvent(Long id) {
    lActiveEvents.lock();
    try {
      activeEvents.remove(id);
    } finally {
      lActiveEvents.unlock();
    }
  }
}
