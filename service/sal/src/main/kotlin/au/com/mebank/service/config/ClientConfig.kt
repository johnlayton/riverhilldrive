package au.com.mebank.service.config

import au.com.mebank.service.client.DemoClient
import au.com.mebank.service.client.DemoWebClient
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.validation.Valid

//@ConstructorBinding
//@EnableConfigurationProperties(ClientConfig::class)
//@Configuration
//@ConfigurationProperties(prefix = "service")
//data class ClientProperties(
//        val address: String
//)
//
@ConstructorBinding
@Configuration
@EnableConfigurationProperties(ClientConfig::class)
@ConfigurationProperties(prefix = "service")
class ClientConfig(
      @Valid private var address : String = ""
) {


//  @Value("\${service.address}")
//  internal var address: String? = null

  @Bean
  fun client(): DemoClient {
    return DemoWebClient(address)
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