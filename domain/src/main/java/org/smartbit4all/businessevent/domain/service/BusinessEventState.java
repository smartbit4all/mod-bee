/*******************************************************************************
 * Copyright (C) 2020 - 2020 it4all Hungary Kft.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
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
