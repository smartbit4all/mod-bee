package org.smartbit4all.businessevent.domain.service;

import java.time.Duration;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartbit4all.businessevent.domain.service.BusinessEventChannel.ChannelType;
import org.smartbit4all.core.SB4FunctionImpl;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class RescheduleEventImpl extends SB4FunctionImpl<BusinessEventSchedule, Void>
    implements RescheduleEvent {

  private static final Logger LOG = LoggerFactory.getLogger(RescheduleEventImpl.class);
  
  private ProcessEvent originalEventProcess;

  public RescheduleEventImpl(ProcessEvent originalEventProcess) {
    super();
    this.originalEventProcess = originalEventProcess;
  }

  @Override
  public void execute() throws Exception {
    BusinessEventChannelService channel = originalEventProcess.getChannel();
    BusinessEventData eventData = originalEventProcess.input();
    BusinessEventState eventStateRescheduled =
        channel.saveEventProcessReschedule(eventData,
            originalEventProcess.getEventState(), input);

    if (channel.getType() == ChannelType.SYNCHRONOUS
        || (channel.getType() == ChannelType.ASYNCHRONOUS
            && originalEventProcess.getEventState().state != BusinessEventStateEnum.A)) {
      BusinessEventState eventStateCopy =
          BusinessEventState.of(originalEventProcess.getEventState());
      TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {

        @Override
        public void afterCommit() {
          if (channel.getType() == ChannelType.SYNCHRONOUS) {
            try {
              
              waitIfNeeded(eventData);
              channel.reprocessSync(eventData, eventStateRescheduled);
              
            } catch (Exception e) {
              throw new RuntimeException(e);
            }
          } else {
            channel.processSchedule(eventData, eventStateCopy);
          }
        }
        
        private void waitIfNeeded(BusinessEventData eventData) {
          Duration after = input.getAfter();
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
        
      });
    }

  }

  @Override
  public RescheduleEvent after(Duration duration) {
    setInput(new BusinessEventSchedule(duration));
    return this;
  }

  @Override
  public RescheduleEvent at(LocalDateTime nextProcessTime) {
    setInput(new BusinessEventSchedule(nextProcessTime));
    return this;
  }

}
