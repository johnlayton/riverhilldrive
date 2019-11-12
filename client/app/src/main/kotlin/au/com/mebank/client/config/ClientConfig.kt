package au.com.mebank.client.config

import au.com.mebank.service.DemoClient
import au.com.mebank.service.DemoWebClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient


@Configuration
class ClientConfig {

  @Value("\${service.address}")
  internal var address: String? = null

  @Bean
  fun client(): DemoClient {
    return DemoWebClient(address!!)
  }

//  @Bean
//  fun client(): WebClient {
//
//    return WebClient.builder()
//        .baseUrl(address!!)
///*
//        .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/vnd.github.v3+json")
//        .defaultHeader(HttpHeaders.USER_AGENT, "Spring 5 WebClient")
//*/
//        .build();
//
//  }

}