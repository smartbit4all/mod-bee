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
package org.smartbit4all.bee.app;

import org.smartbit4all.businessevent.domain.config.BusinessEventEntityConfigX;
import org.smartbit4all.domain.meta.MetaConfiguration;
import org.smartbit4all.sql.config.SQLConfig;
import org.smartbit4all.ui.vaadin.localization.TranslationProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
@Import({MetaConfiguration.class, SQLConfig.class,
    BusinessEventEntityConfigX.class})
@ComponentScan("org.smartbit4all.bee.api.impl")
@ComponentScan("org.smartbit4all.bee.app")
public class BeeAppVaadinAll extends SpringBootServletInitializer {
  public static void main(String[] args) {
    System.setProperty("vaadin.i18n.provider", TranslationProvider.class.getName());
    SpringApplication.run(BeeAppVaadinAll.class, args);
  }

}
