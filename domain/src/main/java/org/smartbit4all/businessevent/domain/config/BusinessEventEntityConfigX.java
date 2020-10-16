package org.smartbit4all.businessevent.domain.config;

import org.smartbit4all.businessevent.domain.entity.EventProcessLogExtendedDef;
import org.springframework.context.annotation.Bean;

public class BusinessEventEntityConfigX extends BusinessEventEntityConfig {

  @Bean(EventProcessLogExtendedDef.ENTITY_NAME)
  public EventProcessLogExtendedDef eventProcessLogExtendedDef() {
    return createEntityProxy(EventProcessLogExtendedDef.class);
  }

}
