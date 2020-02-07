package au.com.mebank.client

import au.com.mebank.client.app.model.InputResponse
import com.github.jenspiegsa.wiremockextension.ConfigureWireMock
import com.github.jenspiegsa.wiremockextension.InjectServer
import com.github.jenspiegsa.wiremockextension.WireMockExtension
import com.github.jenspiegsa.wiremockextension.WireMockSettings
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(
        WireMockExtension::class,
        SpringExtension::class
)
@WireMockSettings(failOnUnmatchedRequests = true)
class IntegrationTest {

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

    @InjectServer
    var serverMock: WireMockServer? = null

    @ConfigureWireMock
    var options = wireMockConfig()
            .port(8091)
//            .notifier(ConsoleNotifier(true))
//            .dynamicPort()

    @LocalServerPort
    var randomServerPort: Int = 0

    @Test
    fun demo() {
        givenThat(post("/demo").willReturn(
                aResponse().withBody(demoResponse)
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
                }
                .block()
        assertEquals(2, response!!.id)
        assertEquals("mock", response.name)
    }
}

