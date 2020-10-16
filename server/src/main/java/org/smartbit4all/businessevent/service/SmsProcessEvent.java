package org.smartbit4all.businessevent.service;

import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartbit4all.businessevent.domain.service.BusinessEventData;
import org.smartbit4all.businessevent.domain.service.ProcessEvent;
import org.smartbit4all.core.SB4FunctionImpl;
import org.smartbit4all.core.utility.BinaryData;

public class SmsProcessEvent extends SB4FunctionImpl<BusinessEventData, BinaryData>
    implements ProcessEvent {

  private static final Logger log = LoggerFactory.getLogger(SmsProcessEvent.class);

  private Random rnd = new Random();

  @Override
  public void execute() throws Exception {
    // System.out.println("The " + input + " event process completed.");
    int sleepTime = 50 + rnd.nextInt(251);
    Thread.sleep(sleepTime);
  }

  @Override
  public ProcessEvent event(BusinessEventData event) {
    input = event;
    return this;
  }

}
