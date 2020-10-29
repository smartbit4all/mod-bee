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

  private BusinessEventChannel channel;

  private BusinessEventState eventState;

  @Override
  public ProcessEvent event(BusinessEventData event) {
    this.input = event;
    return this;
  }

  @Override
  public RescheduleEvent reschedule() {
    RescheduleEvent rescheduleEvent = new RescheduleEventImpl(this);
    post().call(rescheduleEvent);
    return rescheduleEvent;
  }

  @Override
  public ProcessEvent channel(BusinessEventChannel channel) {
    this.channel = channel;
    return this;
  }

  @Override
  public BusinessEventChannel getChannel() {
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
