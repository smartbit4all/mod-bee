/*******************************************************************************
 * Copyright (C) 2020 - 2020 it4all Hungary Kft.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
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
