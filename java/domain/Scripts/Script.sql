grant connect, resource, create any sequence, create any view to BUSINESSEVENT identified by BUSINESSEVENT;

ALTER USER BUSINESSEVENT DEFAULT tablespace USERS;

alter user BUSINESSEVENT default tablespace users;
alter user BUSINESSEVENT temporary tablespace temp;
alter user BUSINESSEVENT quota unlimited on users;

CREATE SEQUENCE id_event;
