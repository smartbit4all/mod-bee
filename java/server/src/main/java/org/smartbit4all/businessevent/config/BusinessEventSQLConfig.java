package org.smartbit4all.businessevent.config;

import org.smartbit4all.sql.config.SQLDBParameter;
import org.smartbit4all.sql.config.SQLDBParameterBase;
import org.smartbit4all.sql.config.SQLDBParameterH2;
import org.smartbit4all.sql.config.SQLDBParameterOracle;
import org.smartbit4all.sql.config.SQLConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Configuration
@Import(SQLConfig.class)
public class BusinessEventSQLConfig {

  @Bean(name = SQLDBParameterBase.DEFAULT)
  @Profile({"dev", "oracle"})
  SQLDBParameter oracleParameter() {
    SQLDBParameter result = new SQLDBParameterOracle();
    return result;
  }

  @Bean(name = SQLDBParameterBase.DEFAULT)
  @Profile("int")
  SQLDBParameter h2Parameter() {
    SQLDBParameter result = new SQLDBParameterH2();
    return result;
  }

}
