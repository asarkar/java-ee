package name.abhijitsarkar.coffeehouse.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Abhijit Sarkar
 */

@Configuration
@ComponentScan(basePackages = "name.abhijitsarkar.coffeehouse.spring")
public class AppConfig {

    // I could've used @Component on the bean but that'd destroy it's Spring agnostic nature.
    @Bean
    public Menu menu() {
        return new Menu();
    }
}
