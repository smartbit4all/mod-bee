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
import org.smartbit4all.types.binarydata.BinaryData;

@Entity(EventBinaryContentDef.ENTITY_NAME)
@Table(EventBinaryContentDef.TABLE_NAME)
public interface EventBinaryContentDef extends EntityDefinition {

  public static final String ENTITY_NAME = "EventBinaryContentDef";
  public static final String TABLE_NAME = "EventBinaryContent";

  public static final String EVENTBINARYCONTENT_ID = "EVENTBINARYCONTENT_ID";
  public static final String EVENTBINARYCONTENT_ID_COL = "EVENTBINARYCONTENT_ID";

  public static final String LOBDATA = "LOBDATA";
  public static final String LOBDATA_COL = "LOBDATA";

  public static final String MIMETYPE = "MIMETYPE";
  public static final String MIMETYPE_COL = "MIMETYPE";

  @OwnProperty(name = EVENTBINARYCONTENT_ID, columnName = EVENTBINARYCONTENT_ID_COL)
  @Id
  Property<Long> eventBinaryContentId();

  @OwnProperty(name = LOBDATA, columnName = LOBDATA_COL)
  Property<BinaryData> lobData();

  @OwnProperty(name = MIMETYPE, columnName = MIMETYPE_COL)
  Property<String> mimeType();

  @Override
  EntityService<EventBinaryContentDef> services();
}
