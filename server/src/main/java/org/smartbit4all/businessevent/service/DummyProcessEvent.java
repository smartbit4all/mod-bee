package org.smartbit4all.businessevent.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartbit4all.businessevent.domain.service.BusinessEventData;
import org.smartbit4all.businessevent.domain.service.ProcessEvent;
import org.smartbit4all.core.SB4FunctionImpl;
import org.smartbit4all.types.binarydata.BinaryData;

public class DummyProcessEvent extends SB4FunctionImpl<BusinessEventData, BinaryData>
    implements ProcessEvent {

  private static final Logger log = LoggerFactory.getLogger(DummyProcessEvent.class);

  @Override
  public void execute() throws Exception {
    System.out.println("The " + input + " event process completed.");
    // output = "OK";
    // throw new Exception("Alma");
  }

  @Override
  public ProcessEvent event(BusinessEventData event) {
    input = event;
    return this;
  }

}
