package au.com.mebank.rest.service.app.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.web.reactive.config.WebFluxConfigurer
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger.web.InMemorySwaggerResourcesProvider
import springfox.documentation.swagger.web.SwaggerResource
import springfox.documentation.swagger.web.SwaggerResourcesProvider
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux

@Configuration
@EnableSwagger2WebFlux
class SwaggerConfig() : WebFluxConfigurer {

  init {
    println("+++++++++++++++++++")
    println("+++ swagger +++++++")
    println("+++++++++++++++++++")
  }

  @Bean
  fun api(): Docket {
    return Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.any())
        .build();
  }

  @Bean
  @Primary
  fun swaggerResourcesProvider(defaultResourcesProvider: InMemorySwaggerResourcesProvider): SwaggerResourcesProvider {
    return SwaggerResourcesProvider {
      val swaggerResource = SwaggerResource()
      swaggerResource.setName("Rest Service")
      swaggerResource.setLocation("/demo.yaml")
      listOf(swaggerResource)
    }
  }

//  override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
//    registry.addResourceHandler("/demo.yaml")
//        .addResourceLocations("classpath:/")
//  }

}