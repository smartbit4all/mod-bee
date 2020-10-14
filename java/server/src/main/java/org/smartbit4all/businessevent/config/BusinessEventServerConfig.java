package org.smartbit4all.businessevent.config;

import org.smartbit4all.businessevent.domain.config.BusinessEventDomainConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({BusinessEventDomainConfig.class,
    BusinessEventServiceConfigSpec.class,
    BusinessEventSQLConfig.class})
public class BusinessEventServerConfig {


}
