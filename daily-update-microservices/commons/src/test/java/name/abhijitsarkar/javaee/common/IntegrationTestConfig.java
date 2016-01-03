package name.abhijitsarkar.javaee.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Abhijit Sarkar
 */
@Configuration
public class IntegrationTestConfig {
    @Bean
    RandomNumberGenerator randomNumberGenerator() {
        return new RandomNumberGenerator();
    }
}
