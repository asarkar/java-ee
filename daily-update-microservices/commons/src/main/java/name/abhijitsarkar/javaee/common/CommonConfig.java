package name.abhijitsarkar.javaee.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Abhijit Sarkar
 */
@Configuration
public class CommonConfig {
    @Bean
    public ObjectMapper objectMapper() {
        return ObjectMapperFactory.newInstance();
    }
}
