package name.abhijitsarkar.javaee.feign;

import name.abhijitsarkar.javaee.feign.matcher.HeadersMatcher;
import name.abhijitsarkar.javaee.feign.matcher.PathMatcher;
import name.abhijitsarkar.javaee.feign.matcher.QueryParamsMatcher;
import name.abhijitsarkar.javaee.feign.matcher.RequestBodyMatcher;
import name.abhijitsarkar.javaee.feign.matcher.RequestMethodMatcher;
import name.abhijitsarkar.javaee.feign.model.Body;
import name.abhijitsarkar.javaee.feign.model.FeignMapping;
import name.abhijitsarkar.javaee.feign.model.FeignProperties;
import name.abhijitsarkar.javaee.feign.model.RequestProperties;
import name.abhijitsarkar.javaee.feign.model.ResponseProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.AbstractMap;
import java.util.Map;

import static java.util.Collections.list;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toMap;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.util.CollectionUtils.isEmpty;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author Abhijit Sarkar
 */
@RestController
public class FeignController {
    @Autowired
    private FeignProperties feignProperties;

    @RequestMapping(path = "/**", method = GET, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<String> all(HttpServletRequest request, @RequestBody(required = false) String body) {
        Map<String, String> queryParams = request.getParameterMap().entrySet().stream()
                .map(e -> new AbstractMap.SimpleImmutableEntry<String, String>(e.getKey(), e.getValue()[0]))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));

        Map<String, String> headers = list(request.getHeaderNames()).stream()
                .collect(toMap(name -> name, request::getHeader));

        FeignMapping feignMapping = feignProperties.getMappings().stream()
                .filter(mapping -> {
                    RequestProperties rp = mapping.getRequest();

                    return new PathMatcher(request.getServletPath())
                            .and(new RequestMethodMatcher(request.getMethod()))
                            .and(new QueryParamsMatcher(queryParams))
                            .and(new HeadersMatcher(headers))
                            .and(new RequestBodyMatcher(body))
                            .test(rp);
                })
                .findAny()
                .orElseThrow(() -> new RuntimeException("No match found."));

        ResponseProperties rp = feignMapping.getResponse();
        HttpHeaders httpHeaders = new HttpHeaders();

        if (!isEmpty(rp.getHeaders())) {
            rp.getHeaders().entrySet()
                    .forEach(e -> httpHeaders.put(e.getKey(), singletonList(e.getValue())));
        }

        Body responseBody = rp.getBody();

        if (responseBody != null) {
            return new ResponseEntity<String>(responseBody.toString(), httpHeaders, HttpStatus.valueOf(rp.getStatus()));
        }

        return new ResponseEntity<String>(httpHeaders, HttpStatus.valueOf(rp.getStatus()));
    }
}
