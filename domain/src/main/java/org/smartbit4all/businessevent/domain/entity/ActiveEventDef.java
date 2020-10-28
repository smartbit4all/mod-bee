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