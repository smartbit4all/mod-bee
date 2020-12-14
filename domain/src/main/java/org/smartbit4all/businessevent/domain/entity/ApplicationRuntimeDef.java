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
import org.smartbit4all.domain.annotation.property.OwnProperty;
import org.smartbit4all.domain.annotation.property.Table;
import org.smartbit4all.domain.meta.EntityDefinition;
import org.smartbit4all.domain.meta.EntityService;
import org.smartbit4all.domain.meta.Property;

@Entity(ApplicationRuntimeDef.ENTITY_NAME)
@Table(ApplicationRuntimeDef.TABLE_NAME)
public interface ApplicationRuntimeDef extends EntityDefinition {

  public static final String ENTITY_NAME = "ApplicationRuntimeDef";
  public static final String TABLE_NAME = "ApplicationRuntime";

  public static final String APPLICATIONRUNTIME_ID = "APPLICATIONRUNTIME_ID";
  public static final String APPLICATIONRUNTIME_ID_COL = "APPLICATIONRUNTIME_ID";

  public static final String STARTTIME = "STARTTIME";
  public static final String STARTTIME_COL = "STARTTIME";

  public static final String STOPTIME = "STOPTIME";
  public static final String STOPTIME_COL = "STOPTIME";

  public static final String LASTTOUCHTIME = "LASTTOUCHTIME";
  public static final String LASTTOUCHTIME_COL = "LASTTOUCHTIME";

  public static final String CODE = "CODE";
  public static final String CODE_COL = "CODE";

  @OwnProperty(name = APPLICATIONRUNTIME_ID, columnName = APPLICATIONRUNTIME_ID_COL)
  @Id
  Property<Long> applicationRuntimeId();

  @OwnProperty(name = STARTTIME, columnName = STARTTIME_COL)
  Property<LocalDateTime> startTime();

  @OwnProperty(name = STOPTIME, columnName = STOPTIME_COL)
  Property<LocalDateTime> stopTime();

  @OwnProperty(name = LASTTOUCHTIME, columnName = LASTTOUCHTIME_COL)
  Property<LocalDateTime> lastTouchTime();

  @OwnProperty(name = CODE, columnName = CODE_COL)
  Property<String> code();

  @Override
  EntityService<ApplicationRuntimeDef> services();
}
