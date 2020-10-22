package org.smartbit4all.businessevent.ui;

import org.smartbit4all.businessevent.domain.config.BusinessEventEntityConfigX;
import org.smartbit4all.domain.meta.MetaConfiguration;
import org.smartbit4all.json.config.JsonConfig;
import org.smartbit4all.sql.config.SQLConfig;
import org.smartbit4all.ui.vaadin.localization.TranslationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

/**
 * The entry point of the Spring Boot application.
 */
@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
@Import({MetaConfiguration.class, SQLConfig.class, 
    JsonConfig.class, BusinessEventEntityConfigX.class})
public class BeeUiApp extends SpringBootServletInitializer {

  public static void main(String[] args) {
    System.setProperty("spring.devtools.restart.enabled", "false");
    System.setProperty("vaadin.i18n.provider", TranslationProvider.class.getName());
    SpringApplication.run(BeeUiApp.class, args);
  }

  @Autowired
  ApplicationContext ctx;

  // @Bean(initMethod = "start", destroyMethod = "stop")
  // @Profile({ "mock" })
  // public Server inMemoryH2DatabaseaServer() throws SQLException {
  // return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9191");
  // }

}
