package org.smartbit4all.businessevent.domain.service;

import java.time.Duration;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartbit4all.businessevent.domain.service.BusinessEventChannel.ChannelType;

public class RescheduleEventImpl implements RescheduleEvent {

  private static final Logger LOG = LoggerFactory.getLogger(RescheduleEventImpl.class);
  
  private ProcessEvent originalEventProcess;
  
  private BusinessEventSchedule time;

  private BusinessEventState eventStateRescheduled;

  public RescheduleEventImpl(ProcessEvent originalEventProcess) {
    super();
    this.originalEventProcess = originalEventProcess;
  }
  
  @Override
  public void saveEvent() throws Exception {
    BusinessEventChannelService channel = originalEventProcess.getChannel();
    BusinessEventData eventData = originalEventProcess.input();
    eventStateRescheduled =
        channel.saveEventProcessReschedule(eventData,
            originalEventProcess.getEventState(), time);
  }
  
  @Override
  public void doReschedule() throws Exception {
    BusinessEventChannelService channel = originalEventProcess.getChannel();
    BusinessEventData eventData = originalEventProcess.input();
    if (channel.getType() == ChannelType.SYNCHRONOUS) {
      waitIfNeeded(eventData);
      channel.processSync(eventData, eventStateRescheduled);

    } else if (channel.getType() == ChannelType.ASYNCHRONOUS && originalEventProcess.getEventState().state != BusinessEventStateEnum.A){
      BusinessEventState eventStateCopy =
          BusinessEventState.of(originalEventProcess.getEventState());
      channel.processSchedule(eventData, eventStateCopy);
    }
  }

  private void waitIfNeeded(BusinessEventData eventData) {
    Duration after = time.getAfter();
    if (after != null && after.toMillis() > 0) {
      try {
        
        Thread.sleep(after.toMillis());
        
      } catch (InterruptedException e) {
        String interruptedMessage =
            "Thread is interrupted during synchronous reschedule waiting of business event. EventData: "
                + eventData.toString();

        LOG.error(interruptedMessage, e);

        throw new RuntimeException(interruptedMessage, e);
      }
    }
  }

  @Override
  public RescheduleEvent after(Duration duration) {
    time = new BusinessEventSchedule(duration);
    return this;
  }

  @Override
  public RescheduleEvent at(LocalDateTime nextProcessTime) {
    time = new BusinessEventSchedule(nextProcessTime);
    return this;
  }
  
  @Override
  public BusinessEventSchedule time() {
    return time;
  }

}
