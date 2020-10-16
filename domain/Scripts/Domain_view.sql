create or replace view eventprocesslog_extended
as
select epl.*, eb.createdat, eb.eventchannel,
   trunc(1000 * (extract(second from processfinishtime - processstarttime)
    + 60 * (extract(minute from processfinishtime - processstarttime)
    + 60 * (extract(hour from processfinishtime - processstarttime)
    + 24 * (extract(day from processfinishtime - processstarttime) ))))) processtime,
   trunc(1000 * (extract(second from processstarttime - eb.createdat)
    + 60 * (extract(minute from processstarttime - eb.createdat)
    + 60 * (extract(hour from processstarttime - eb.createdat)
    + 24 * (extract(day from processstarttime - eb.createdat) ))))) delay
from eventprocesslog epl, eventbody eb
where epl.eventbody_id = eb.eventbody_id;
