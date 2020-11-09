package org.smartbit4all.businessevent.domain.service;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @author Peter Boros
 *
 */
public class BusinessEventSchedule {

  Duration after;

  LocalDateTime at;

  public BusinessEventSchedule(LocalDateTime at) {
    super();
    this.at = at;
  }

  public BusinessEventSchedule(Duration after) {
    super();
    this.after = after;
  }

  /**
   * Calculates the next process time based on the current time.
   * 
   * @param currentTime
   * @return
   */
  public LocalDateTime getNextProcessTime(LocalDateTime currentTime) {
    if (at != null) {
      return at.isBefore(currentTime) ? currentTime : at;
    } else if (after != null) {
      return currentTime.plus(after);
    }
    return currentTime;
  }

  public Duration getAfter() {
    return after;
  }

}
