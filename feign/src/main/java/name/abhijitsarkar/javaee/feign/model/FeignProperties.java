package name.abhijitsarkar.javaee.feign.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Abhijit Sarkar
 */
@Component
@ConfigurationProperties(prefix = "feign")
@Data
public class FeignProperties {
    private List<FeignMapping> mappings;
}
