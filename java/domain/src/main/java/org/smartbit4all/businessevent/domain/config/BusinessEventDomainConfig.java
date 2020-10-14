package org.smartbit4all.businessevent.domain.config;

import org.smartbit4all.domain.config.DomainConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({DomainConfig.class, BusinessEventEntityConfigX.class,
    BusinessEventServiceConfig.class})
public class BusinessEventDomainConfig {

}
