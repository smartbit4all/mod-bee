package org.smartbit4all.businessevent.service;

import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartbit4all.businessevent.domain.service.ProcessEventBase;

public class SmsProcessEvent extends ProcessEventBase {

  private static final Logger log = LoggerFactory.getLogger(SmsProcessEvent.class);

  private Random rnd = new Random();

  @Override
  public void execute() throws Exception {
    // System.out.println("The " + input + " event process completed.");
    int sleepTime = 50 + rnd.nextInt(251);
    Thread.sleep(sleepTime);
  }

}
