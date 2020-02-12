package au.com.mebank.client

import au.com.mebank.client.app.model.InputResponse
//import au.com.mebank.soap.service.sal.config.ClientConfig
//import au.com.mebank.soap.service.sal.config.ClientProperties
import com.github.tomakehurst.wiremock.client.WireMock
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.testcontainers.containers.DockerComposeContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.io.File


class MeDockerComposeContainer(file: File) : DockerComposeContainer<MeDockerComposeContainer>(file)

@ExtendWith(
    SpringExtension::class
)
//@ConfigurationPropertiesScan(
//    "au.com.mebank.soap.service.app.config"
//)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = [
      "service.address=http://localhost:8080/demo",
      "spring.main.web-application-type=reactive",
      "spring.banner.image.location=classpath:/logo.png",
      "spring.banner.image.width=75",
      "spring.banner.image.height=100"
    ])
//@EnableConfigurationProperties(ClientProperties::class)
@Testcontainers
@ActiveProfiles("test")
class ApplicationTest {

  @Container
  val container: MeDockerComposeContainer =
      MeDockerComposeContainer(File("src/test/resources/docker-compose.yml"))
          .withExposedService("wiremock_1", 8080, Wait.forListeningPort())
          .withTailChildContainers(true)
//                    .withExposedService("wiremock_1", 8080, Wait.forHttp("/"))

  private var host: String? = null

  private var port: Int = 0

  @BeforeEach
  fun setup() {
    host = container.getServiceHost("wiremock_1", 8080)
    port = container.getServicePort("wiremock_1", 8080)
  }

  private val demoRequest = """
{
    "id"   : 1,
    "name" : "john"
}                    
""".trimIndent()

  private val demoResponse = """
{
    "id"   : 2,
    "name" : "mock"
}                    
""".trimIndent()

  @LocalServerPort
  var randomServerPort: Int = 0

  @Test
  fun demo() {
    val wireMock = WireMock("localhost", 8080)
    wireMock.register(WireMock.post("/demo").willReturn(
        WireMock.aResponse().withBody(demoResponse)
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
    ));

    val client = WebClient.builder().baseUrl("http://localhost:$randomServerPort").build()
    val response = client.post()
        .uri("/demo")
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(demoRequest))
        .exchange()
        .flatMap {
          it.bodyToMono(InputResponse::class.java)
        } as Mono<InputResponse>

    StepVerifier.create(response)
        .assertNext { resp ->
          assertEquals(2, resp.id)
          assertEquals("mock", resp.name)
        }
        .expectComplete()
        .verify()

//        assertEquals(2, response!!.id)
//        assertEquals("mock", response.name)

    wireMock.findAllUnmatchedRequests()
  }
}