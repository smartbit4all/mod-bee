package org.smartbit4all.businessevent.service;

import org.smartbit4all.businessevent.domain.service.BusinessEventProcessService;
import org.smartbit4all.businessevent.domain.service.ProcessEvent;

public class SmsProcessEventService implements BusinessEventProcessService {

  @Override
  public ProcessEvent process() {
    return new SmsProcessEvent();
  }

}
