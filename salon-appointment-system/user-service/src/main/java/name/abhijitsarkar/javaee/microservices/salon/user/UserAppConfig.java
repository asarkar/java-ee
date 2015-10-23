package name.abhijitsarkar.javaee.microservices.salon.user;

import org.h2.server.web.WebServlet;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import name.abhijitsarkar.javaee.microservices.salon.user.domain.User;

@SpringBootApplication
@EnableWebMvcSecurity
@EntityScan(basePackageClasses = User.class)
public class UserAppConfig {
	@Bean
	ServletRegistrationBean h2servletRegistration() {
		ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());
		registrationBean.addUrlMappings("/console/*");
		return registrationBean;
	}

	@Bean
	public Module jdk8Module() {
		return new Jdk8Module();
	}
}
