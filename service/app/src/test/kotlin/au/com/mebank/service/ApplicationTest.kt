package au.com.mebank.service

import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder
import org.junit.jupiter.api.Assertions.assertEquals
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

class MeDockerComposeContainer(file: File) : DockerComposeContainer<MeDockerComposeContainer>(file)

@Testcontainers
class ApplicationTest {

    @Container
    val container : MeDockerComposeContainer =
            MeDockerComposeContainer(File("src/test/resources/docker-compose.yml"))
            .withExposedService("nginx_1", 80, Wait.forHttp("/"))
            .withExposedService("wiremock_1", 8080,  Wait.forHttp("/"))


    private var host: String? = null

    private var port: Int = 0

    @BeforeEach
    fun setup() {
        host = container.getServiceHost("wiremock_1", 8080)
        port = container.getServicePort("wiremock_1", 8080)
    }

    @Test
    @Throws(Exception::class)
    fun simpleTest() {
        val client = HttpClientBuilder.create().build()
        val response = client.execute(HttpGet("http://$host:$port/__admin/webapp"))

        assertEquals(200, response.getStatusLine().getStatusCode())

//        val client = WebClient.builder().baseUrl("http://$host:$port").build()
//        val response = client.get()
//                .uri("/")
////                .contentType(MediaType.APPLICATION_JSON)
////                .body(BodyInserters.fromValue(""demoRequest""))
//                .exchange()
////                .flatMap {
////                    it.bodyToMono(InputResponse::class.java)
////                }
//                .block()
//
//        println(response)

//        val client = HttpClientBuilder.create().build()
//
//        val response = client.execute(HttpGet("http://$host:$port"))
//
//        assertEquals(200, response.getStatusLine().getStatusCode())
    }
}

//object DockerContainer {
//    val instance: KDockerComposeContainer by lazy { startDockerCompose()}
//    class KDockerComposeContainer(file: File) : DockerComposeContainer<KDockerComposeContainer>(file)
//
//
//    private fun startDockerCompose() = KDockerComposeContainer(File("integrationTests/docker-compose.start-archival.yml"))
//
//}