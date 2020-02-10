package au.com.mebank.client.app

import au.com.mebank.client.app.handler.InputHandler
import au.com.mebank.client.app.model.InputResponse
import au.com.mebank.client.app.router.InputRouter
import com.github.jenspiegsa.wiremockextension.ConfigureWireMock
import com.github.jenspiegsa.wiremockextension.InjectServer
import com.github.jenspiegsa.wiremockextension.WireMockExtension
import com.github.jenspiegsa.wiremockextension.WireMockSettings
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.givenThat
import com.github.tomakehurst.wiremock.client.WireMock.post
import com.github.tomakehurst.wiremock.common.ConsoleNotifier
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import org.junit.Before
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.context.ApplicationContext
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import reactor.test.StepVerifier
import org.springframework.test.web.reactive.server.*

//@SpringBootTest(
//    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
//)
@WebFluxTest
@ExtendWith(
    WireMockExtension::class,
    SpringExtension::class
)
@WireMockSettings(
    failOnUnmatchedRequests = true
)
//@ContextConfiguration(
//    classes = arrayOf(
//      Application::class,
//      InputHandler::class,
//      InputRouter::class
//    )
//)
class IntegrationTest(@Autowired val client: WebTestClient) {

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

//  @LocalServerPort
//  var randomServerPort: Int = 0
//
//  @Autowired
//  private lateinit var inputHandler: InputHandler
//
//  @Autowired
//  private lateinit var applicationContext: ApplicationContext

//  @Autowired
//  private lateinit var webTestClient: WebTestClient

//  @BeforeEach
//  fun setUp() {
//    webTestClient = WebTestClient.bindToApplicationContext(context).build()
//  }


  @Test
  fun testGetInputResponse() {
//    val client = WebClient.builder()
//        .baseUrl("http://localhost:$randomServerPort").build()

//    val client = WebTestClient.bindToApplicationContext(context)
//        .ba
//        .build()

    val response = client.get()
        .uri("/demo")
        .exchange()
        .expectBody()
        .consumeWith({ response ->
          println(response)
        })
//        .value<String::class.java>() {
//
//
//
//        }

//        .flatMap {
//          //                    it.bodyToMono(InputResponse::class.java)
//          it.bodyToMono(String::class.java)Reactive
//        }
//        .map {
//          println(it)
//          it
//        }
//
//    StepVerifier.create(response)
//        .assertNext {
//          it
//
//          println(it)
//
//          assertNotNull(it)
//        }
//        .verifyComplete()
  }


  @Test
  fun testPostInputRequest() {

    givenThat(post("/service/demo").willReturn(
        aResponse().withBody(Soap.demoResponse)
            .withStatus(200)
            .withHeader("Content-Type", "application/soap+xml")
    ));

//    val client = WebClient.builder()
//        .baseUrl("http://localhost:$randomServerPort").build()

    val response = client.post()
        .uri("/demo")
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(Rest.inputRequest))
        .exchange()
        .expectStatus().isOk()
        .expectBody<InputResponse>()
        .isEqualTo(InputResponse(1, "Hello john!!!"))
//    (InputResponse::class.java)
//        .value({ employee1 ->
//          employee.getAge(), equalTo(23)
//        })
//        .flatMap {
//          //                    it.bodyToMono(InputResponse::class.java)
//          it.bodyToMono(String::class.java)
//        }
//        .map {
//          println(it)
//          it
//        }
//                .block()

//        StepVerifier.create(inputHandler.sayHello(mock()))
//                .assertNext {
//                    assertNotNull(it)
//                }
//                .verifyComplete()

//    StepVerifier.create(response)
//        .assertNext {
//          it
//
//          println(it)
//
//          assertNotNull(it)
//        }
//        .verifyComplete()

//        assertNotNull(response)
//
//        assertEquals(1, response!!.id)
//        assertEquals("Hello john!!!", response.name)
  }
}

