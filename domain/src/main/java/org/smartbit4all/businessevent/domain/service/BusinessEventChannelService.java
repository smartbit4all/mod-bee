package org.smartbit4all.businessevent.domain.service;

import java.time.LocalDateTime;
import java.util.List;
import org.smartbit4all.businessevent.domain.service.BusinessEventChannel.ChannelOperationMode;
import org.smartbit4all.businessevent.domain.service.BusinessEventChannel.ChannelType;

/**
 * The inner service that implements the functionalities of a channel.
 * 
 * @author Peter Boros
 */
public interface BusinessEventChannelService {

  /**
   * @see ChannelOperationMode
   * @param mode
   */
  void setMode(ChannelOperationMode mode);

  /**
   * Start the operation of the given channel.
   */
  void start();

  /**
   * Stop the operation of the channel.
   */
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

  void setChannelCode(String channelCode);

  int getExecutionThreadLimit();

  void setExecutionThreadLimit(int executionThreadLimit);

  int getMaxEventQueueSize();

  void setMaxEventQueueSize(int maxEventQueueSize);

  int getNormalOperationLimit();

  void setNormalOperationLimit(int normalOperationLimit);

  int getAllocationLimit();

  void setAllocationLimit(int allocationLimit);

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
   * The reschedule means that we already have an event that must be scheduled again.
   * 
   * @param event The event that already exists
   * @param schedule The timing of the next execution
   * @return The updated event state
   * @throws Exception
   */
  BusinessEventState saveEventProcessReschedule(BusinessEventData event,
      BusinessEventState currentState,
      BusinessEventSchedule schedule) throws Exception;

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
      ProcessEvent processEvent)
      throws Exception;

  /**
   * Save the fact that the given sync events have been failed.
   * 
   * @param failedEvents The sync events failed.
   * @throws Exception
   */
  void saveEventSyncProcessFailed(List<BusinessEventState> failedStates)
      throws Exception;

  /**
   * Registers the scheduled function as an event with the unique identifier generated from the
   * function itself. It will be the class name of the function.
   * 
   * TODO Later on it must be the fully qualified access name of the function.
   * 
   * @param scheduledFunction
   * @throws Exception
   */
  void registerScheduledFunction(ScheduledFunction<?, ?> scheduledFunction) throws Exception;

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
  void setSelf(BusinessEventChannelService self);

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

  /**
   * Set the channel type that can be {@link ChannelType#SYNCHRONOUS} or
   * {@link ChannelType#ASYNCHRONOUS}.
   * 
   * @param type
   */
  void setType(ChannelType type);

  /**
   * Returns the channel type that can be {@link ChannelType#SYNCHRONOUS} or
   * {@link ChannelType#ASYNCHRONOUS}.
   * 
   * @return
   */
  ChannelType getType();

  String getChannelCode();

}
