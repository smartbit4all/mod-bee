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

@Entity(ActiveEventDef.ENTITY_NAME)
@Table(ActiveEventDef.TABLE_NAME)
public interface ActiveEventDef extends EntityDefinition {

  public static final String ENTITY_NAME = "ActiveEventDef";
  public static final String TABLE_NAME = "ActiveEvent";

  public static final String ACTIVEEVENT_ID = "ACTIVEEVENT_ID";
  public static final String ACTIVEEVENT_ID_COL = "ACTIVEEVENT_ID";

  public static final String EVENTBODY_ID = "EVENTBODY_ID";
  public static final String EVENTBODY_ID_COL = "EVENTBODY_ID";

  public static final String NEXTPROCESSTIME = "NEXTPROCESSTIME";
  public static final String NEXTPROCESSTIME_COL = "NEXTPROCESSTIME";

  public static final String APPLICATIONRUNTIME_ID = "APPLICATIONRUNTIME_ID";
  public static final String APPLICATIONRUNTIME_ID_COL = "APPLICATIONRUNTIME_ID";

  public static final String EVENTCHANNEL = "EVENTCHANNEL";
  public static final String EVENTCHANNEL_COL = "EVENTCHANNEL";

  public static final String STATE = "STATE";
  public static final String STATE_COL = "STATE";

  @OwnProperty(name = ACTIVEEVENT_ID, columnName = ACTIVEEVENT_ID_COL)
  @Id
  Property<Long> activeEventId();

  @OwnProperty(name = EVENTBODY_ID_COL, columnName = EVENTBODY_ID)
  Property<Long> eventbodyId();

  @ReferenceEntity
  @Join(source = EVENTBODY_ID, target = EventBodyDef.EVENTBODY_ID)
  EventBodyDef eventbody();

  @OwnProperty(name = NEXTPROCESSTIME, columnName = NEXTPROCESSTIME_COL)
  Property<LocalDateTime> nextProcessTime();

  @OwnProperty(name = APPLICATIONRUNTIME_ID_COL, columnName = APPLICATIONRUNTIME_ID)
  Property<Long> applicationruntimeId();

  @ReferenceEntity
  @Join(source = APPLICATIONRUNTIME_ID, target = ApplicationRuntimeDef.APPLICATIONRUNTIME_ID)
  ApplicationRuntimeDef applicationruntime();

  @OwnProperty(name = EVENTCHANNEL, columnName = EVENTCHANNEL_COL)
  Property<String> eventChannel();

  @OwnProperty(name = STATE, columnName = STATE_COL)
  Property<String> state();

  @Override
  EntityService<ActiveEventDef> services();
}
