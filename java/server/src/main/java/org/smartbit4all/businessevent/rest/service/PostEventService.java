package org.smartbit4all.businessevent.rest.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartbit4all.businessevent.domain.entity.ApplicationRuntimeDef;
import org.smartbit4all.businessevent.domain.service.BusinessEventChannel;
import org.smartbit4all.businessevent.domain.service.BusinessEventData;
import org.smartbit4all.businessevent.domain.service.BusinessEventService;
import org.smartbit4all.businessevent.domain.service.PostEvent;
import org.smartbit4all.businessevent.rest.model.BusinessEventRequest;
import org.smartbit4all.businessevent.rest.model.BusinessEventState;
import org.smartbit4all.businessevent.rest.model.BusinessEventState.StateEnum;
import org.smartbit4all.domain.application.TimeManagementService;
import org.smartbit4all.domain.data.TableData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostEventService implements PostEventApiDelegate {

  public static final Logger log = LoggerFactory.getLogger(PostEventService.class);

  @Autowired
  private BusinessEventService beService;

  @Autowired
  private ApplicationRuntimeDef appRuntime;

  @Autowired
  private TimeManagementService timeManagementService;

  @Override
  @Transactional(isolation = Isolation.READ_COMMITTED)
  public ResponseEntity<BusinessEventState> postEvent(BusinessEventRequest body) throws Exception {
    log.info("postEvent");

    appRuntime.services().crud().query().all().lock()
        .into(new TableData<>(appRuntime)).execute();

    BusinessEventChannel eventChannel = beService.channel(body.getChannel());
    BusinessEventData event = new BusinessEventData();
    event.actionCode = body.getActionCode();
    event.businessEntity = body.getBusinessEntity();
    event.businessEntityRef = body.getBusinessEntityRef();
    event.channel = body.getChannel();
    event.extensionText = body.getExtensionText();
    event.sessionId = body.getSessionId();
    if (body.getNextProcessTime() != null) {
      event.nextProcessTime = body.getNextProcessTime().toLocalDateTime();
    } else {
      event.nextProcessTime = timeManagementService.getSystemTime();
    }
    PostEvent postEvent = eventChannel.post().event(event);
    postEvent.execute();
    BusinessEventState result = new BusinessEventState();
    org.smartbit4all.businessevent.domain.service.BusinessEventState output = postEvent.output();
    // TODO Here we need more accurate result!
    result.setEventId(output.dbId);
    result.setChannel(body.getChannel());
    result.setNumberOfProcessing(Long.valueOf(0));
    result.setState(StateEnum.WAITING);
    return ResponseEntity.ok(result);
  }

}
