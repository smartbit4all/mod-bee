package org.smartbit4all.businessevent.domain.service;

import org.smartbit4all.core.SB4FunctionImpl;
import org.smartbit4all.core.utility.BinaryData;

public abstract class ProcessEventBase extends SB4FunctionImpl<BusinessEventData, BinaryData> implements ProcessEvent {

  @Override
  public ProcessEvent event(BusinessEventData event) {
    this.input = event;
    return this;
  }
  
}
