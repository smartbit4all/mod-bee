package org.smartbit4all.businessevent.domain.service;

import java.time.LocalDateTime;
import java.util.UUID;
import org.smartbit4all.businessevent.domain.entity.ActiveEventDef;
import org.smartbit4all.businessevent.domain.entity.EventBodyDef;
import org.smartbit4all.types.binarydata.BinaryData;

public class BusinessEventState {

  /**
   * The unique identifier of the event.
   */
  public UUID identifier;

  /**
   * The id of the {@link ActiveEventDef} and {@link EventBodyDef} record. It's the same for the
   * sake of simplicity.
   */
  public Long dbId;

  /**
   * The state of the given event.
   */
  public BusinessEventStateEnum state;

  /**
   * The number of previous execution. The current execution is not counted. So in the first
   * ProcessEvent it's 0.
   */
  public Long previousExecutions = 0l;

  /**
   * The expected next process time for the event.
   */
  public LocalDateTime nextProcessTime;

  /**
   * It's the process ID that is currently running.
   */
  public Long processId;

  /**
   * On the API it could be useful to have the data reference. It can be null!
   */
  public transient BusinessEventData eventData;

  /**
   * The result of the event execution. It can be null if there is no result or we don't want to
   * user the result outside of the event.
   */
  public BinaryData result;

  public boolean rescheduled = false;

  public static BusinessEventState of(BusinessEventState other) {
    BusinessEventState result = new BusinessEventState();
    result.dbId = other.dbId;
    result.identifier = other.identifier;
    result.state = other.state;
    result.previousExecutions = other.previousExecutions;
    result.processId = other.processId;
    result.nextProcessTime = other.nextProcessTime;
    return result;
  }

}
