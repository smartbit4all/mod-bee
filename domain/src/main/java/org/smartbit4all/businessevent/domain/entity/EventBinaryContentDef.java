package org.smartbit4all.businessevent.domain.entity;

import org.smartbit4all.core.utility.BinaryData;
import org.smartbit4all.domain.annotation.property.Entity;
import org.smartbit4all.domain.annotation.property.Id;
import org.smartbit4all.domain.annotation.property.OwnProperty;
import org.smartbit4all.domain.annotation.property.Table;
import org.smartbit4all.domain.meta.EntityDefinition;
import org.smartbit4all.domain.meta.EntityService;
import org.smartbit4all.domain.meta.Property;

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