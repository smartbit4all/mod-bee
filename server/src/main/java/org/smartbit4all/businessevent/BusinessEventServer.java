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
package org.smartbit4all.businessevent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartbit4all.businessevent.config.BusinessEventServerConfig;
import org.smartbit4all.businessevent.domain.service.ApplicationRuntimeService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({BusinessEventServerConfig.class})
public class BusinessEventServer {

  private static final Logger log = LoggerFactory.getLogger(BusinessEventServer.class);

  public static void main(String[] args) {
    ConfigurableApplicationContext applicationContext =
        SpringApplication.run(BusinessEventServer.class, args);
    ApplicationRuntimeService service = applicationContext.getBean(ApplicationRuntimeService.class);
    Long appRuntimeId = service.getAppRuntimeId();
    log.info("The application is registered with {} identifier.", appRuntimeId);
  }
}
