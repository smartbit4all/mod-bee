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
