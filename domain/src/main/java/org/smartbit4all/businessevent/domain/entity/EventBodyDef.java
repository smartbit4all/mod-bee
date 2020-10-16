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



@Entity(EventBodyDef.ENTITY_NAME)
@Table(EventBodyDef.TABLE_NAME)
public interface EventBodyDef extends EntityDefinition {

	public static final String ENTITY_NAME = "EventBodyDef";
	public static final String TABLE_NAME = "EventBody";

	public static final String IDENTIFIER = "IDENTIFIER";
	public static final String IDENTIFIER_COL = "IDENTIFIER";

	public static final String ACTIONCODE = "ACTIONCODE";
	public static final String ACTIONCODE_COL = "ACTIONCODE";

	public static final String CREATEDAT = "CREATEDAT";
	public static final String CREATEDAT_COL = "CREATEDAT";

	public static final String BUSINESSENTITY = "BUSINESSENTITY";
	public static final String BUSINESSENTITY_COL = "BUSINESSENTITY";

	public static final String BUSINESSENTITYREF = "BUSINESSENTITYREF";
	public static final String BUSINESSENTITYREF_COL = "BUSINESSENTITYREF";

	public static final String EXTENSIONTEXT = "EXTENSIONTEXT";
	public static final String EXTENSIONTEXT_COL = "EXTENSIONTEXT";

	public static final String SESSIONID = "SESSIONID";
	public static final String SESSIONID_COL = "SESSIONID";

	public static final String EVENTCHANNEL = "EVENTCHANNEL";
	public static final String EVENTCHANNEL_COL = "EVENTCHANNEL";

	public static final String EVENTBODY_ID = "EVENTBODY_ID";
	public static final String EVENTBODY_ID_COL = "EVENTBODY_ID";

	public static final String REQUEST_ID = "REQUEST_ID";
	public static final String REQUEST_ID_COL = "REQUEST_ID";

	public static final String LASTPROCESSLOG_ID = "LASTPROCESSLOG_ID";
	public static final String LASTPROCESSLOG_ID_COL = "LASTPROCESSLOG_ID";

	@OwnProperty(name = EVENTBODY_ID, columnName = EVENTBODY_ID_COL)
	@Id
	Property<Long> eventBodyId();

	@OwnProperty(name = IDENTIFIER, columnName = IDENTIFIER_COL)
	Property<String> identifier();

	@OwnProperty(name = ACTIONCODE, columnName = ACTIONCODE_COL)
	Property<String> actionCode();

	@OwnProperty(name = CREATEDAT, columnName = CREATEDAT_COL)
	Property<LocalDateTime> createdAt();

	@OwnProperty(name = BUSINESSENTITY, columnName = BUSINESSENTITY_COL)
	Property<String> businessEntity();

	@OwnProperty(name = BUSINESSENTITYREF, columnName = BUSINESSENTITYREF_COL)
	Property<String> businessEntityRef();

	@OwnProperty(name = EXTENSIONTEXT, columnName = EXTENSIONTEXT_COL)
	Property<String> extensionText();

	@OwnProperty(name = SESSIONID, columnName = SESSIONID_COL)
	Property<String> sessionId();

	@OwnProperty(name = EVENTCHANNEL, columnName = EVENTCHANNEL_COL)
	Property<String> eventChannel();

	@OwnProperty(name = REQUEST_ID_COL, columnName = REQUEST_ID)
	Property<Long> requestId();

	@ReferenceEntity
	@Join(source = REQUEST_ID, target = EventBinaryContentDef.EVENTBINARYCONTENT_ID)
	EventBinaryContentDef request();

	@OwnProperty(name = LASTPROCESSLOG_ID_COL, columnName = LASTPROCESSLOG_ID)
	Property<Long> lastProcessLogId();

	@ReferenceEntity
	@Join(source = LASTPROCESSLOG_ID, target = EventProcessLogDef.EVENTPROCESSLOG_ID)
	EventProcessLogDef lastProcessLog();

	@Override
	EntityService<EventBodyDef> services();

}
