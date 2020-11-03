package org.smartbit4all.bee.app;

import org.smartbit4all.ui.vaadin.localization.TranslationProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
@ComponentScan("org.smartbit4all.bee.api.impl")
@ComponentScan("org.smartbit4all.bee.app")
public class BeeAppVaadinMock extends SpringBootServletInitializer {
  public static void main(String[] args) {
    System.setProperty("vaadin.i18n.provider", TranslationProvider.class.getName());
    SpringApplication.run(BeeAppVaadinMock.class, args);
  }

}
