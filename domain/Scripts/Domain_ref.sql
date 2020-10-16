ALTER TABLE EventBody ADD CONSTRAINT REF_EventBody_request FOREIGN KEY (REQUEST_ID) REFERENCES EventBinaryContent (EVENTBINARYCONTENT_ID);

ALTER TABLE EventBody ADD CONSTRAINT REF_EventBody_lastProcessLog FOREIGN KEY (LASTPROCESSLOG_ID) REFERENCES EventProcessLog (EVENTPROCESSLOG_ID);

ALTER TABLE ActiveEvent ADD CONSTRAINT REF_ActiveEvent_eventbody FOREIGN KEY (EVENTBODY_ID) REFERENCES EventBody (EVENTBODY_ID);

ALTER TABLE ActiveEvent ADD CONSTRAINT REF_ActiveEvent_applicationruntime FOREIGN KEY (APPLICATIONRUNTIME_ID) REFERENCES ApplicationRuntime (APPLICATIONRUNTIME_ID);



ALTER TABLE EventProcessLog ADD CONSTRAINT REF_EventProcessLog_eventbody FOREIGN KEY (EVENTBODY_ID) REFERENCES EventBody (EVENTBODY_ID);

ALTER TABLE EventProcessLog ADD CONSTRAINT REF_EventProcessLog_result FOREIGN KEY (RESULT_ID) REFERENCES EventBinaryContent (EVENTBINARYCONTENT_ID);

ALTER TABLE EventProcessLog ADD CONSTRAINT REF_EventProcessLog_applicationruntime FOREIGN KEY (APPLICATIONRUNTIME_ID) REFERENCES ApplicationRuntime (APPLICATIONRUNTIME_ID);



