package org.smartbit4all.businessevent.domain.service;

import java.time.Duration;
import java.time.LocalDateTime;
import org.smartbit4all.businessevent.domain.service.BusinessEventChannel.ChannelType;
import org.smartbit4all.core.SB4FunctionImpl;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class RescheduleEventImpl extends SB4FunctionImpl<BusinessEventSchedule, Void>
    implements RescheduleEvent {

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
              channel.processSync(eventData,
                  eventStateRescheduled);
            } catch (Exception e) {
              throw new RuntimeException(e);
            }
          } else {
            channel.processSchedule(eventData, eventStateCopy);
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
