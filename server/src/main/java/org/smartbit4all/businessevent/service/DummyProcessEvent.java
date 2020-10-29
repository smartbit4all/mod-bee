package org.smartbit4all.businessevent.service;

import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartbit4all.businessevent.domain.service.ProcessEventBase;

public class DummyProcessEvent extends ProcessEventBase {

  private static final Logger log = LoggerFactory.getLogger(DummyProcessEvent.class);

  @Override
  public void execute() throws Exception {
    System.out.println("The " + input + " event process completed.");
    if (getEventState().previousExecutions == 0) {
      reschedule().after(Duration.ofSeconds(5));
    }
    // output = "OK";
    // throw new Exception("Alma");
  }

}
