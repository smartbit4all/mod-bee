package org.smartbit4all.businessevent.domain.service;

import java.time.Duration;
import java.time.LocalDateTime;
import org.smartbit4all.core.SB4Function;

public interface RescheduleEvent extends SB4Function<BusinessEventSchedule, Void> {

  RescheduleEvent after(Duration duration);

  RescheduleEvent at(LocalDateTime nextProcessTime);

}
