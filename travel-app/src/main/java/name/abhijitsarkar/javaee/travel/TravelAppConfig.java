package name.abhijitsarkar.javaee.travel;

import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Abhijit Sarkar
 */
@Configuration
@EnableCouchbaseRepositories
public class TravelAppConfig extends AbstractCouchbaseConfiguration {
    @Value("${COUCHBASE_NODES}")
    private String couchbaseNodes;

    @Value("${COUCHBASE_BUCKET}")
    private String couchbaseBucket;

    @Override
    protected List<String> getBootstrapHosts() {
        return Arrays.asList(couchbaseNodes.split("\\s*,\\s*"));
    }

    @Override
    protected String getBucketName() {
        return couchbaseBucket;
    }

    @Override
    protected String getBucketPassword() {
        return "";
    }

    @Override
    protected CouchbaseEnvironment getEnvironment() {
        return DefaultCouchbaseEnvironment.builder()
                .connectTimeout(TimeUnit.SECONDS.toMillis(10))
                .computationPoolSize(3)
                .queryEnabled(true)
                .build();
    }
}
