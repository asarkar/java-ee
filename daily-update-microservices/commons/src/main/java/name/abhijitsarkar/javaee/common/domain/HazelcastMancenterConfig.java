package name.abhijitsarkar.javaee.common.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Abhijit Sarkar
 */
@ConfigurationProperties(prefix = "hazelcast")
@Component
@Data
public class HazelcastMancenterConfig {
    private String mancenterURL;
    private boolean mancenterEnabled;
}
