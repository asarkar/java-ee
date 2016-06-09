package name.abhijitsarkar.javaee.feign

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.boot.test.TestRestTemplate
import org.springframework.boot.test.WebIntegrationTest
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import spock.lang.Specification

import javax.annotation.PostConstruct

/**
 * @author Abhijit Sarkar
 */
@SpringApplicationConfiguration(FeignApp)
@WebIntegrationTest(randomPort = true)
abstract class AbstractFeignSpec extends Specification {
    @Autowired
    FeignController feignController

    @Value('${local.server.port}')
    int port

    protected RestTemplate restTemplate = new TestRestTemplate()

    protected UriComponentsBuilder uriBuilder

    @PostConstruct
    def postConstruct() {
        uriBuilder = UriComponentsBuilder.fromUriString("http://localhost:$port")
    }
}
