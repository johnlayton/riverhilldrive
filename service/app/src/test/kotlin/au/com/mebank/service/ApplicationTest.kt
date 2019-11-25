package au.com.mebank.service

import org.testcontainers.containers.DockerComposeContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.io.File
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient


@Testcontainers
class ApplicationTest {

    @Container
    val container : DockerComposeContainer<Nothing> = DockerComposeContainer<Nothing>("", listOf(File("src/test/resources/docker-compose.yml")))
            .withExposedService("nginx_1", 8080, Wait.forHttp("/"))


    private var host: String? = null

    private var port: Int = 0

    @BeforeEach
    fun setup() {
        host = container.getServiceHost("nginx_1", 8080)
        port = container.getServicePort("nginx_1", 8080)
    }

    @Test
    @Throws(Exception::class)
    fun simpleTest() {

        val client = WebClient.builder().baseUrl("http://$host:$port").build()
        val response = client.get()
                .uri("/")
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(BodyInserters.fromValue(""demoRequest""))
                .exchange()
//                .flatMap {
//                    it.bodyToMono(InputResponse::class.java)
//                }
                .block()

        println(response)

//        val client = HttpClientBuilder.create().build()
//
//        val response = client.execute(HttpGet("http://$host:$port"))
//
//        assertEquals(200, response.getStatusLine().getStatusCode())
    }
}