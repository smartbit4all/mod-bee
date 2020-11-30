package org.smartbit4all.businessevent.domain.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.smartbit4all.businessevent.domain.service.BusinessEventChannel.ChannelType;
import org.smartbit4all.core.SB4Function;
import org.smartbit4all.core.SB4FunctionImpl;
import org.smartbit4all.domain.application.TimeManagementService;

class PostEventImpl extends SB4FunctionImpl<BusinessEventData, BusinessEventState>
    implements PostEvent {

  private BusinessEventChannelService channel;
  private TimeManagementService timeService;

  private SB4Function<?, ?> function;

  public PostEventImpl(BusinessEventChannelService channel, TimeManagementService timeService) {
    super();
    this.channel = channel;
    this.timeService = timeService;
  }

  @Override
  public void execute() throws Exception {

    BusinessEventState eventState = null;
    LocalDateTime startTime = timeService.getSystemTime();
    if (channel.getType() == ChannelType.SYNCHRONOUS) {
      // In case of synchronous execution we start the execution immediately so we setup the event
      // with under execution state.
      // TODO Do in separate transaction to persist the state before start the event process.
      eventState = channel.saveNewEvent(input, BusinessEventStateEnum.E, startTime);

      channel.processSync(input, eventState);
    } else if (channel.getType() == ChannelType.ASYNCHRONOUS) {
      // Now we will start the execution asynchronously on another thread. We can't wait the
      // execution directly! We have to know if we start the execution right after the successful
      // transaction.
      
      BusinessEventStateEnum state = calculateState();
      eventState =
          channel.saveNewEvent(input, state, input.nextProcessTime);
      
      if (state == BusinessEventStateEnum.W) {
        BusinessEventState eventStateCopy = BusinessEventState.of(eventState);
        channel.processSchedule(input, eventStateCopy);
      }
    }
    output = eventState;
  }

  /**
   * Two case (depends on {@link BusinessEventChannelImpl#LOOK_AHAED_LIMIT}):
   * <p> 
   * -  1. start immediately --> persist, start at the end of the successful
   * transaction.
   * <p>  
   * -  2. Store and wait for maintenance
   * <p> 
   * TODO Parameterize {@link BusinessEventChannelImpl#LOOK_AHAED_LIMIT}
   * @return
   */
  private BusinessEventStateEnum calculateState() {
    LocalDateTime now = timeService.getSystemTime();
    BusinessEventStateEnum state;
    long delay;
    if (input.nextProcessTime == null || now.isAfter(input.nextProcessTime)) {
      // The scheduling delay is 0. We can start immediately.
      delay = 0;
    } else {
      delay = ChronoUnit.SECONDS.between(now, input.nextProcessTime);
    }
    if (delay > BusinessEventChannelImpl.LOOK_AHAED_LIMIT) {
      state = BusinessEventStateEnum.A;
    } else {
      state = BusinessEventStateEnum.W;
    }
    return state;
  }

  @Override
  public PostEvent event(BusinessEventData event) {
    setInput(event);
    return this;
  }

  @Override
  public PostEvent function(SB4Function<?, ?> function) {
    this.function = function;
    // We construct the BusinessEventData for the function. We will be able to serialize/deserialize
    // them.
    BusinessEventData eventData = new BusinessEventData();
    eventData.actionCode = function.getClass().getName();
    eventData.channel = channel.getChannelCode();
    eventData.businessEntity = "FUNCTION";
    return event(eventData);
  }

}
