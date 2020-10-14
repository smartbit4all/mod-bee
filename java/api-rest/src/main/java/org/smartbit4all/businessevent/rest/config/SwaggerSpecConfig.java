package org.smartbit4all.businessevent.rest.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.InMemorySwaggerResourcesProvider;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerSpecConfig {

  @Primary
  @Bean
  public SwaggerResourcesProvider swaggerResourcesProvider(
      InMemorySwaggerResourcesProvider defaultResourcesProvider) {
    return () -> {
      List<SwaggerResource> resources = new ArrayList<>();
      resources.add(loadResource("api1"));
      return resources;
    };
  }

  private SwaggerResource loadResource(String resource) {
    SwaggerResource wsResource = new SwaggerResource();
    wsResource.setName(resource);
    wsResource.setSwaggerVersion("2.0");
    wsResource.setLocation("/swagger-apis/" + resource + "/business-event.yaml");
    return wsResource;
  }

  @Bean
  public Docket swagger() {
    return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.any()).build();
  }
}
