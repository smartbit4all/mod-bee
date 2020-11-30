package org.smartbit4all.businessevent.domain.service;

import org.smartbit4all.core.SB4FunctionImpl;
import org.smartbit4all.types.binarydata.BinaryData;

/**
 * The basic implementation of the {@link ProcessEvent} interface.
 * 
 * @author Peter Boros
 */
public abstract class ProcessEventBase extends SB4FunctionImpl<BusinessEventData, BinaryData>
    implements ProcessEvent {

  private BusinessEventChannelService channel;

  private BusinessEventState eventState;
  
  private RescheduleEvent rescheduleEvent;

  @Override
  public ProcessEvent event(BusinessEventData event) {
    this.input = event;
    return this;
  }

  @Override
  public RescheduleEvent reschedule() {
    if (rescheduleEvent == null) {
      rescheduleEvent = new RescheduleEventImpl(this);
    }
    return rescheduleEvent;
  }
  
  @Override
  public boolean rescheduleNeeded() {
    return rescheduleEvent != null;
  }

  @Override
  public ProcessEvent channel(BusinessEventChannelService channel) {
    this.channel = channel;
    return this;
  }

  @Override
  public BusinessEventChannelService getChannel() {
    return channel;
  }

  @Override
  public ProcessEvent eventState(BusinessEventState eventState) {
    this.eventState = eventState;
    return this;
  }

  @Override
  public BusinessEventState getEventState() {
    return eventState;
  }

}
