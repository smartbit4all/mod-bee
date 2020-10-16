create index eventbody_idx_001 on eventbody(lastprocesslog_id);

create index eventprocesslog_idx_002 on eventprocesslog(eventbody_id);

create index activeevent_idx_003 on activeevent(eventbody_id);

CREATE UNIQUE INDEX EVENTCHANNEL_CODE_IDX ON EVENTCHANNEL (CODE);