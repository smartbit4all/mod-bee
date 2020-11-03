package org.smartbit4all.bee.app.config;

import org.smartbit4all.domain.meta.ServiceConfiguration;
import org.smartbit4all.domain.service.identifier.IdentifierService;
import org.smartbit4all.sql.config.SQLDBParameter;
import org.smartbit4all.sql.config.SQLDBParameterBase;
import org.smartbit4all.sql.config.SQLDBParameterH2;
import org.smartbit4all.sql.config.SQLDBParameterOracle;
import org.smartbit4all.sql.service.identifier.SQLIdentifierService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class ServiceConfigImpl extends ServiceConfiguration {

  @Bean
  public IdentifierService identifierService(JdbcTemplate jdbcTemplate) {
    return new SQLIdentifierService(jdbcTemplate);
  }

  @Bean(name = SQLDBParameterBase.DEFAULT)
  @Profile({"dev", "prod", "uat"})
  SQLDBParameter oracleParameter() {
    SQLDBParameter result = new SQLDBParameterOracle();
    return result;
  }

  @Bean(name = SQLDBParameterBase.DEFAULT)
  @Profile({"mock", "test"})
  SQLDBParameter h2Parameter() {
    SQLDBParameter result = new SQLDBParameterH2();
    return result;
  }

}
