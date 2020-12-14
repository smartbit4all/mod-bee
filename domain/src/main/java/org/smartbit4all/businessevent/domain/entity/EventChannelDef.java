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

import org.smartbit4all.domain.annotation.property.Entity;
import org.smartbit4all.domain.annotation.property.Id;
import org.smartbit4all.domain.annotation.property.OwnProperty;
import org.smartbit4all.domain.annotation.property.Table;
import org.smartbit4all.domain.meta.EntityDefinition;
import org.smartbit4all.domain.meta.EntityService;
import org.smartbit4all.domain.meta.Property;

@Entity(EventChannelDef.ENTITY_NAME)
@Table(EventChannelDef.TABLE_NAME)
public interface EventChannelDef extends EntityDefinition {

  public static final String ENTITY_NAME = "EventChannelDef";
  public static final String TABLE_NAME = "EventChannel";

  public static final String EVENTCHANNEL_ID = "EVENTCHANNEL_ID";
  public static final String EVENTCHANNEL_ID_COL = "EVENTCHANNEL_ID";

  public static final String CODE = "CODE";
  public static final String CODE_COL = "CODE";

  @OwnProperty(name = EVENTCHANNEL_ID, columnName = EVENTCHANNEL_ID_COL)
  @Id
  Property<Long> eventChannelId();

  @OwnProperty(name = CODE, columnName = CODE_COL)
  Property<String> code();

  @Override
  EntityService<EventChannelDef> services();
}
