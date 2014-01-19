package name.abhijitsarkar.webservices.jaxws.instrumentation.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "name.abhijitsarkar.webservices.jaxws.instrumentation.client")
public class DispatcherConfig {
}
