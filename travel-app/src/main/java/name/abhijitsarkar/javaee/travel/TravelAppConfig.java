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

/**
 * @author Abhijit Sarkar
 */
@Configuration
public class TravelAppConfig {
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
}
