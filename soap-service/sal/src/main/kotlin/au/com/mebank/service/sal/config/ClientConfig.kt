package au.com.mebank.service.sal.config

import au.com.mebank.service.DemoService
import au.com.mebank.service.sal.client.DemoClient
import au.com.mebank.service.sal.client.DemoWebClient
import au.com.mebank.service.sal.config.ClientProperties
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.jws.soap.SOAPBinding
import javax.validation.Valid

//@ConstructorBinding
//@EnableConfigurationProperties(ClientConfig::class)
//@Configuration
//@ConfigurationProperties(prefix = "service")
//data class ClientProperties(
//        val address: String
//)
//
@Configuration
@EnableConfigurationProperties(ClientProperties::class)
//@ConfigurationProperties(prefix = "service")
class ClientConfig(@Valid private val clientProperties: ClientProperties) {
//@ConstructorBinding
//constructor


//  @Value("\${service.address}")
//  internal var address: String? = null

//  data class Account(val bsb : String,
//                     val account : String)

  @Bean
  fun client(demoService : DemoService): DemoClient {
//    val account : Account = Regex("([0-9]{6})\\-([0-9]{9})").find("foo")?.destructured?.let { (bsb, account) -> Account() } ?: Account()
//    { (bsb, account) ->
//      return Account(bsb, account)
//    }



    return DemoWebClient(demoService)
//    return DemoWebClient(clientProperties.address)
  }

  @Bean
  fun demoService() : DemoService {
    val jaxWsProxyFactoryBean = JaxWsProxyFactoryBean()
    jaxWsProxyFactoryBean.serviceClass = DemoService::class.java
    jaxWsProxyFactoryBean.address = clientProperties.address
//    jaxWsProxyFactoryBean.bindingId = SOAPBinding.SOAP12HTTP_BINDING
//    jaxWsProxyFactoryBean.features.add(customSoapFeature)

    return jaxWsProxyFactoryBean.create() as DemoService
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