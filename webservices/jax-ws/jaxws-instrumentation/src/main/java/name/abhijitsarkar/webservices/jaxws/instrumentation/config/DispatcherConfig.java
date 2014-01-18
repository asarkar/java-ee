package name.abhijitsarkar.webservices.jaxws.instrumentation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "name.abhijitsarkar.webservices.jaxws.instrumentation.client")
public class DispatcherConfig {
	@Bean
	public InternalResourceViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();

		// Relative to context root
		resolver.setPrefix("/pages/");
		resolver.setSuffix(".jsp");

		return resolver;
	}
}
