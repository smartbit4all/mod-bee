package org.smartbit4all.businessevent.ui.config;

import org.smartbit4all.domain.meta.ServiceConfiguration;
import org.smartbit4all.domain.service.identifier.IdentifierService;
import org.smartbit4all.sql.service.identifier.SQLIdentifierService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class ServiceConfigImpl extends ServiceConfiguration {

  @Bean
  public IdentifierService identifierService(JdbcTemplate jdbcTemplate) {
    return new SQLIdentifierService(jdbcTemplate);
  }
  
}
