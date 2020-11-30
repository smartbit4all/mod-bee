package org.smartbit4all.businessevent.domain.service;

import java.time.Duration;
import java.time.LocalDateTime;

public interface RescheduleEvent {

  RescheduleEvent after(Duration duration);

  RescheduleEvent at(LocalDateTime nextProcessTime);
  
  BusinessEventSchedule time();
  
  // TODO move these from RescheduleEvent API
  void saveEvent() throws Exception;
  
  void doReschedule() throws Exception;

}
