package au.com.mebank.client.app

import au.com.mebank.client.app.handler.InputHandler
import au.com.mebank.client.app.model.InputResponse
import com.github.jenspiegsa.wiremockextension.ConfigureWireMock
import com.github.jenspiegsa.wiremockextension.InjectServer
import com.github.jenspiegsa.wiremockextension.WireMockExtension
import com.github.jenspiegsa.wiremockextension.WireMockSettings
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.common.ConsoleNotifier
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import com.nhaarman.mockitokotlin2.mock
import org.apache.catalina.Server
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.server.ServerRequest
import reactor.test.StepVerifier

@SpringBootTest(
//        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT

)
@ExtendWith(
        WireMockExtension::class,
        SpringExtension::class
)
@WireMockSettings(
        failOnUnmatchedRequests = true
)
class IntegrationTest {

    object Rest {
        val inputRequest = """
{
    "id"   : 1,
    "name" : "john"
}                    
""".trimIndent()

        val inputResponse = """
{
    "id"   : 1,
    "name" : "hello john"
}                    
""".trimIndent()
    }

    object Soap {
        val demoRequest = """
<soap:Envelope 
  xmlns:soap="http://www.w3.org/2003/05/soap-envelope" 
  xmlns:ser="http://mebank.com.au/service">
   <soap:Header/>
   <soap:Body>
      <ser:sayHello>
         <request>
            <id>1</id>
            <name>john</name>
         </request>
      </ser:sayHello>
   </soap:Body>
</soap:Envelope>
""".trimIndent()

        val demoResponse = """
<soap:Envelope 
  xmlns:soap="http://www.w3.org/2003/05/soap-envelope">
   <soap:Body>
      <ns1:sayHelloResponse xmlns:ns1="http://mebank.com.au/service">
         <response xmlns:ns2="http://mebank.com.au/service">
            <id>1</id>
            <name>Hello john!!!</name>
         </response>
      </ns1:sayHelloResponse>
   </soap:Body>
</soap:Envelope>
""".trimIndent()
    }

    @InjectServer
    var serverMock: WireMockServer? = null

    @ConfigureWireMock
    var options = wireMockConfig()
            .port(8092)
            .notifier(ConsoleNotifier(true))
//            .dynamicPort()

//    @LocalServerPort
    var randomServerPort: Int = 9091

    @Autowired
    private lateinit var inputHandler: InputHandler

    @Test
    fun demo() {

        givenThat(post("/service/demo").willReturn(
                aResponse().withBody(Soap.demoResponse)
                        .withStatus(200)
                        .withHeader("Content-Type", "application/soap+xml")
        ));

        val client = WebClient.builder()
                .baseUrl("http://localhost:$randomServerPort").build()

        val response = client.post()
                .uri("/demo")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(Rest.inputRequest))
                .exchange()
                .flatMap {
//                    it.bodyToMono(InputResponse::class.java)
                    it.bodyToMono(String::class.java)
                }
                .map {
                    println(it)
                }
//                .block()

//        StepVerifier.create(inputHandler.sayHello(mock()))
//                .assertNext {
//                    assertNotNull(it)
//                }
//                .verifyComplete()

        StepVerifier.create(response)
                .assertNext { it
                    assertNotNull(it)
                }
                .verifyComplete()




//        assertNotNull(response)
//
//        assertEquals(1, response!!.id)
//        assertEquals("Hello john!!!", response.name)
    }
}

