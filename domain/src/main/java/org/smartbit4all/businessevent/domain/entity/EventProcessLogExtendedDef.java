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
package org.smartbit4all.businessevent.domain.entity;

import java.time.LocalDateTime;
import org.smartbit4all.domain.annotation.property.Entity;
import org.smartbit4all.domain.annotation.property.Id;
import org.smartbit4all.domain.annotation.property.Join;
import org.smartbit4all.domain.annotation.property.OwnProperty;
import org.smartbit4all.domain.annotation.property.ReferenceEntity;
import org.smartbit4all.domain.annotation.property.Table;
import org.smartbit4all.domain.meta.EntityDefinition;
import org.smartbit4all.domain.meta.EntityService;
import org.smartbit4all.domain.meta.Property;

@Entity(EventProcessLogExtendedDef.ENTITY_NAME)
@Table("eventprocesslog_extended")
public interface EventProcessLogExtendedDef extends EntityDefinition {

  public static final String RESULTCODE = "RESULTCODE";
  public static final String RESULTCODE_COL = "RESULTCODE";

  public static final String PROCESSSTARTTIME = "PROCESSSTARTTIME";
  public static final String PROCESSSTARTTIME_COL = "PROCESSSTARTTIME";

  public static final String PROCESSFINISHTIME = "PROCESSFINISHTIME";
  public static final String PROCESSFINISHTIME_COL = "PROCESSFINISHTIME";

  public static final String STATE = "STATE";
  public static final String STATE_COL = "STATE";

  public static final String ORDERNO = "ORDERNO";
  public static final String ORDERNO_COL = "ORDERNO";

  public static final String ALLOCATIONTIME = "ALLOCATIONTIME";
  public static final String ALLOCATIONTIME_COL = "ALLOCATIONTIME";

  public static final String EXPECTEDALLOCATIONTIME = "EXPECTEDALLOCATIONTIME";
  public static final String EXPECTEDALLOCATIONTIME_COL = "EXPECTEDALLOCATIONTIME";

  public static final String EVENTPROCESSLOG_ID = "EVENTPROCESSLOG_ID";
  public static final String EVENTPROCESSLOG_ID_COL = "EVENTPROCESSLOG_ID";

  public static final String EVENTBODY_ID = "EVENTBODY_ID";
  public static final String EVENTBODY_ID_COL = "EVENTBODY_ID";

  public static final String RESULT_ID = "RESULT_ID";
  public static final String RESULT_ID_COL = "RESULT_ID";

  public static final String APPLICATIONRUNTIME_ID = "APPLICATIONRUNTIME_ID";
  public static final String APPLICATIONRUNTIME_ID_COL = "APPLICATIONRUNTIME_ID";

  @OwnProperty(name = EVENTPROCESSLOG_ID, columnName = EVENTPROCESSLOG_ID_COL)
  @Id
  Property<Long> eventProcessLogId();

  @OwnProperty(name = RESULTCODE, columnName = RESULTCODE_COL)
  Property<String> resultCode();

  @OwnProperty(name = PROCESSSTARTTIME, columnName = PROCESSSTARTTIME_COL)
  Property<LocalDateTime> processStartTime();

  @OwnProperty(name = PROCESSFINISHTIME, columnName = PROCESSFINISHTIME_COL)
  Property<LocalDateTime> processFinishTime();

  @OwnProperty(name = STATE, columnName = STATE_COL)
  Property<String> state();

  @OwnProperty(name = ORDERNO, columnName = ORDERNO_COL)
  Property<Long> orderNo();

  @OwnProperty(name = ALLOCATIONTIME, columnName = ALLOCATIONTIME_COL)
  Property<LocalDateTime> allocationTime();

  @OwnProperty(name = EXPECTEDALLOCATIONTIME, columnName = EXPECTEDALLOCATIONTIME_COL)
  Property<LocalDateTime> expectedAllocationTime();

  @OwnProperty(name = EVENTBODY_ID_COL, columnName = EVENTBODY_ID)
  Property<Long> eventbodyId();

  @ReferenceEntity
  @Join(source = EVENTBODY_ID, target = EventBodyDef.EVENTBODY_ID)
  EventBodyDef eventbody();

  @OwnProperty(name = RESULT_ID_COL, columnName = RESULT_ID)
  Property<Long> resultId();

  @ReferenceEntity
  @Join(source = RESULT_ID, target = EventBinaryContentDef.EVENTBINARYCONTENT_ID)
  EventBinaryContentDef result();

  @OwnProperty(name = APPLICATIONRUNTIME_ID_COL, columnName = APPLICATIONRUNTIME_ID)
  Property<Long> applicationruntimeId();

  @ReferenceEntity
  @Join(source = APPLICATIONRUNTIME_ID, target = ApplicationRuntimeDef.APPLICATIONRUNTIME_ID)
  ApplicationRuntimeDef applicationruntime();


  // Extension


  public static final String ENTITY_NAME = "EventProcessLogExtendedDef";

  @OwnProperty(name = "processTime", columnName = "PROCESSTIME")
  Property<Long> processTime();

  @OwnProperty(name = "delay", columnName = "DELAY")
  Property<Long> delay();

  @Override
  EntityService<EventProcessLogExtendedDef> services();
}
