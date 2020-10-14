package org.smartbit4all.businessevent.domain.config;

import org.smartbit4all.businessevent.domain.service.ApplicationRuntimeService;
import org.smartbit4all.businessevent.domain.service.ApplicationRuntimeServiceImpl;
import org.smartbit4all.businessevent.domain.service.BusinessEventIdService;
import org.smartbit4all.businessevent.domain.service.BusinessEventService;
import org.smartbit4all.businessevent.domain.service.BusinessEventServiceImpl;
import org.smartbit4all.core.SB4Configuration;
import org.smartbit4all.domain.meta.MetaConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@Import(MetaConfiguration.class)
@Configuration
@EnableScheduling
public class BusinessEventServiceConfig extends SB4Configuration {

  @Bean(destroyMethod = "onDestroy")
  public BusinessEventService businessEventService() {
    return new BusinessEventServiceImpl();
  }

  @Bean
  public BusinessEventIdService businessEventIdService() {
    return new BusinessEventIdService();
  }

  @Bean
  public ApplicationRuntimeService runtimeService() {
    return new ApplicationRuntimeServiceImpl();
  }

  // @Bean
  // @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  // public BusinessEventChannel channelService() {
  // return new BusinessEventChannel();
  // }

}
