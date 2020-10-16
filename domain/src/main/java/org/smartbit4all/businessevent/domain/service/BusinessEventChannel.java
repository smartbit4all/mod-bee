package org.smartbit4all.businessevent.domain.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

public interface BusinessEventChannel {


  public static enum ChannelType {

    /**
     * The channel has no executor it processes the events on the thread of the caller.
     */
    SYNCHRONOUS,

    /**
     * The channel has a {@link ThreadPoolExecutor} that is responsible for processing the event on
     * a separate thread maintained by an executor.
     */
    ASYNCHRONOUS,

  }

  /**
   * The channels can work in different well defined operation modes.
   * 
   * @author Peter Boros
   */
  public static enum ChannelOperationMode {

    /**
     * The events are processed on every node in a cluster. There is no guarantee for the processing
     * order of the event. This mode is the most effective in the meaning of processing speed. Can
     * use all the resources we have on every node. If there is no relationship between the events
     * and the technology allows to process the events on every node at the same time then it's the
     * best choice.
     */
    ALL_NODE_MAX_THROUGHPUT,

    /**
     * Same as {@link #ALL_NODE_MAX_THROUGHPUT} but it's allocated into one cluster node.
     */
    ONE_NODE_MAX_THROUGHPUT,

    /**
     * The execution of the event is in the order of arrival. By definition it's served with one
     * node.
     */
    FIFO,

    /**
     * The {@link #FIFO} is applied only for the event from the same session. The events can be
     * parallel executed if they are not from the same session.
     */
    FIFO_SESSIONBASED,

    /**
     * The events are executed only by the scheduled times and in this case more than one event can
     * be executed.
     */
    ALL_NODE_BATCH,

    /**
     * The batch execution is always strictly allocated to one runtime node.
     */
    ONE_NODE_BATCH;

  }

  /**
   * The general state of the given channel.
   * 
   * @author Peter Boros
   */
  public static enum ChannelState {

    /**
     * The channel is in stable stopped state. It doesn't accept any event. The post results an
     * exception.
     */
    INACTIVE,

    /**
     * The channel is in a transient phase, the start is in progress. It could be a very short
     * period but under this the events are hold in a temporary queue and waits for the result of
     * the start. When the channel reach the
     */
    STARTING,

    /**
     * The channel is running and accept the posts but store it only into the database.
     */
    ACTIVE_STOREONLY,

    /**
     * The channel is running and accept the posts and start execution immediately.
     */
    ACTIVE_EXECUTE,

    /**
     * The channel is about to shutdown. It stores the posted events but doesn't start execution at
     * all.
     */
    STOPPING

  }

  /**
   * @see ChannelOperationMode
   * @return
   */
  ChannelOperationMode getMode();

  /**
   * @see ChannelOperationMode
   * @param mode
   */
  void setMode(ChannelOperationMode mode);

  /**
   * {@link ChannelState}
   * 
   * @return
   */
  ChannelState getState();

  PostEvent post();

  void start();

  void stop();

  /**
   * The service that provide the {@link ProcessEvent} function for the channel.
   * 
   * @return
   */
  BusinessEventProcessService getProcessService();

  /**
   * The service that provide the {@link ProcessEvent} function for the channel.
   * 
   * @param processService
   */
  void setProcessService(BusinessEventProcessService processService);

  String getChannelCode();

  void setChannelCode(String channelCode);

  int getExecutionThreadLimit();

  void setExecutionThreadLimit(int executionThreadLimit);

  int getMaxEventQueueSize();

  void setMaxEventQueueSize(int maxEventQueueSize);

  int getNormalOperationLimit();

  void setNormalOperationLimit(int normalOperationLimit);

  int getAllocationLimit();

  void setAllocationLimit(int allocationLimit);

  Long getRuntimeId();

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
  BusinessEventState saveNewEvent(BusinessEventData event, BusinessEventStateEnum state,
      LocalDateTime nextProcessTime) throws Exception;

  /**
   * This application runtime starts the execution of the event. This function allocates the given
   * record to avoid duplicated process. Set the application runtime for this record.
   * 
   * @param eventStates The list of events to allocate.
   * @return The updated state.
   * @throws Exception
   */
  void saveEventsAllocated(List<BusinessEventState> eventStates) throws Exception;

  /**
   * Save the fact that we start the processing of the given event. It's only an update on the state
   * of the process log and the active event. At the same time we set the process start time for the
   * process log. The start time will be the current time.
   * 
   * @param event The event itself.
   * @return
   * @throws Exception
   */
  BusinessEventState saveEventProcessStarted(BusinessEventState currentState)
      throws Exception;

  /**
   * Save the result of the event process. It can be successful or failed.
   * 
   * @param event The event itself.
   * @param currentState The current state of the event. Contains the database identifier.
   * @param ex If an exception occurred while processing the event. In this case the result depends
   *        on the value of the nextProcessTime
   * @param nextProcessTime If there is any chance to try again the event process later then this
   *        parameter marks the time. If null then there won't be next process and the active event
   *        will be moved to the process event.
   * @return
   * @throws Exception
   */
  BusinessEventState saveEventProcessFinished(BusinessEventState currentState,
      Exception ex,
      LocalDateTime nextProcessTime)
      throws Exception;

  /**
   * The execution of the event processing in the current thread.
   * 
   * @throws Exception
   */
  void processSync(BusinessEventData event, BusinessEventState state) throws Exception;

  /**
   * The execution of the event processing in Executor thread pool.
   * 
   * @throws Exception
   */
  void processAsync(BusinessEventData event, BusinessEventState state);

  /**
   * The execution of the event scheduled for a given time. It submit this event into the scheduled
   * executor service.
   * 
   * @throws Exception
   */
  void processSchedule(BusinessEventData event, BusinessEventState startingState);

  /**
   * TODO How to access the self interface?
   * 
   * @param self
   */
  void setSelf(BusinessEventChannel self);

  /**
   * The generic schedule function for the channel. The different channels have different
   * implementation.
   * <ul>
   * <li>For the synchronous channel it is the cleanup for the lost events.</li>
   * <li>For the asynchronous channel it is the catch for the lost events.</li>
   * <li>For the scheduled channel it is the cleanup for the lost events.</li>
   * </ul>
   */
  void maintain();

  ChannelType getType();

  void setType(ChannelType type);

}
