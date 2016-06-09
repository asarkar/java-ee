package name.abhijitsarkar.javaee.feign

import name.abhijitsarkar.javaee.feign.model.FeignMapping
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ActiveProfiles

import static org.springframework.http.HttpMethod.GET
import static org.springframework.http.HttpStatus.OK

/**
 * @author Abhijit Sarkar
 */
@ActiveProfiles("p1")
class FeignSpec extends AbstractFeignSpec {
    def "exactly matches request path"() {
        given:
        def uri = uriBuilder.path('abc').build().toUri()

        when:
        def ResponseEntity<String> response =
                restTemplate.exchange(uri, GET, null, FeignMapping)

        then:
        response.statusCode == OK
        response.headers.findAll { it.key == it.value.first() }.collect { it.key } == ['h3', 'h4'] as List
    }
}