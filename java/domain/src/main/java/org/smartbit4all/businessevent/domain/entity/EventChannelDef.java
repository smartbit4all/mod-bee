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

	public static final String CODE = "CODE";
	public static final String CODE_COL = "CODE";

	public static final String EVENTCHANNEL_ID = "EVENTCHANNEL_ID";
	public static final String EVENTCHANNEL_ID_COL = "EVENTCHANNEL_ID";

	@OwnProperty(name = EVENTCHANNEL_ID, columnName = EVENTCHANNEL_ID_COL)
	@Id
	Property<Long> eventChannelId();

	@OwnProperty(name = CODE, columnName = CODE_COL)
	Property<String> code();

	@Override
	EntityService<EventChannelDef> services();

}
