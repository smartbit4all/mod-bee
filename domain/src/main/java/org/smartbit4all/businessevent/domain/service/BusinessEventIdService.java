package org.smartbit4all.businessevent.domain.service;

import org.smartbit4all.domain.service.identifier.IdentifierService;
import org.smartbit4all.domain.service.identifier.NextIdentifier;
import org.springframework.beans.factory.annotation.Autowired;

public class BusinessEventIdService {

  public static final String SEQUENCE_NAME = "id_event";

  @Autowired
  public IdentifierService identifierService;

  public Long getNextId() throws Exception {
    NextIdentifier next = identifierService.next();
    next.setInput(SEQUENCE_NAME);
    next.execute();
    return next.output();
  }

}
