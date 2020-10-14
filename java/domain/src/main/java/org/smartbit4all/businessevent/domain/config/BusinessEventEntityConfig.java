package org.smartbit4all.businessevent.domain.config;

import org.smartbit4all.domain.meta.EntityConfiguration;
import org.smartbit4all.domain.meta.MetaConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.smartbit4all.businessevent.domain.entity.EventBodyDef;
import org.smartbit4all.businessevent.domain.entity.ActiveEventDef;
import org.smartbit4all.businessevent.domain.entity.EventBinaryContentDef;
import org.smartbit4all.businessevent.domain.entity.EventProcessLogDef;
import org.smartbit4all.businessevent.domain.entity.ApplicationRuntimeDef;
import org.smartbit4all.businessevent.domain.entity.EventChannelDef;

@Import(MetaConfiguration.class)
@Configuration
public class BusinessEventEntityConfig extends EntityConfiguration {

  @Bean(EventBodyDef.ENTITY_NAME)
  public EventBodyDef eventBodyDef() {
    return createEntityProxy(EventBodyDef.class);
  }
  @Bean(ActiveEventDef.ENTITY_NAME)
  public ActiveEventDef activeEventDef() {
    return createEntityProxy(ActiveEventDef.class);
  }
  @Bean(EventBinaryContentDef.ENTITY_NAME)
  public EventBinaryContentDef eventBinaryContentDef() {
    return createEntityProxy(EventBinaryContentDef.class);
  }
  @Bean(EventProcessLogDef.ENTITY_NAME)
  public EventProcessLogDef eventProcessLogDef() {
    return createEntityProxy(EventProcessLogDef.class);
  }
  @Bean(ApplicationRuntimeDef.ENTITY_NAME)
  public ApplicationRuntimeDef applicationRuntimeDef() {
    return createEntityProxy(ApplicationRuntimeDef.class);
  }
  @Bean(EventChannelDef.ENTITY_NAME)
  public EventChannelDef eventChannelDef() {
    return createEntityProxy(EventChannelDef.class);
  }
  
}
