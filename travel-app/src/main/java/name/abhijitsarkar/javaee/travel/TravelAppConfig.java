package name.abhijitsarkar.javaee.travel;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Abhijit Sarkar
 */
@Configuration
public class TravelAppConfig extends WebMvcConfigurerAdapter {
    @Value("${COUCHBASE_NODES}")
    private String nodes;

    @Value("${COUCHBASE_BUCKET_NAME}")
    private String bucketName;

    @Value("${COUCHBASE_BUCKET_PASSWORD}")
    private String bucketPassword;

    @Bean
    Cluster cluster() {
        return CouchbaseCluster.create(nodes.split("\\s+"));
    }

    @Bean
    Bucket bucket() {
        return cluster().openBucket(bucketName, bucketPassword);
    }

    @Bean
    public static ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
                .disable(SerializationFeature.WRITE_NULL_MAP_VALUES)
                .enable(SerializationFeature.WRITE_DATES_WITH_ZONE_ID);

        mapper.setPropertyNamingStrategy(
                PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);

        return mapper;
    }

    /* Don't map URLs with suffixes to handlers. Give a chance to ResourceHttpRequestHandler.
    * This is usually not necessary if static resources don't map to URLs for which, if the suffix is stripped off,
    * some handler exists. For example, if /abc maps to a handler and /abc.html to a static page, with suffix
    * pattern matching enabled, the request maps to the handler, not to the ResourceHttpRequestHandler.
    *
    * c.f. WebMvcProperties.staticPathPattern and ResourceProperties for more configuration options.
    */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        super.configurePathMatch(configurer);

        configurer.setUseSuffixPatternMatch(false);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*");
    }
}
