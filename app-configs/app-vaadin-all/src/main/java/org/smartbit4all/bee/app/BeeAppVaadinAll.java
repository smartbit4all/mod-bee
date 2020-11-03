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
