package au.com.mebank.client.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient



@Configuration
class ClientConfig
//@Autowired
constructor(
//    private val endpoint: ServiceEndpoint,
//    bus: Bus,
//    soapFeature: BankfastSoapFeature
) {

  @Bean
  fun client() : WebClient {

    return WebClient.builder()
        .baseUrl("http://localhost:8082")
/*
        .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/vnd.github.v3+json")
        .defaultHeader(HttpHeaders.USER_AGENT, "Spring 5 WebClient")
*/
        .build();

  }

}