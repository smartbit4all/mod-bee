package org.smartbit4all.docu.app;

import org.smartbit4all.ui.vaadin.localization.TranslationProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
@Import(TranslationProvider.class)
public class BeeDevAppVaadin extends SpringBootServletInitializer {
  public static void main(String[] args) {
    System.setProperty("vaadin.i18n.provider", TranslationProvider.class.getName());
    SpringApplication.run(BeeDevAppVaadin.class, args);
  }

}
