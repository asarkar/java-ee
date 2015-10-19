package name.abhijitsarkar.javaee.microservices.salon.appointment;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.h2.server.web.WebServlet;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.ConverterRegistry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import name.abhijitsarkar.javaee.microservices.salon.appointment.service.StringToOffsetDateTimeConverter;

@SpringBootApplication
public class AppointmentAppConfig {
	@Resource(name = "defaultConversionService")
	private ConverterRegistry converterRegistry;

	@PostConstruct
	void init() throws JsonProcessingException {
		converterRegistry.addConverter(new StringToOffsetDateTimeConverter());
	}

	@Bean
	ServletRegistrationBean h2servletRegistration() {
		ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());
		registrationBean.addUrlMappings("/console/*");
		return registrationBean;
	}

	/*
	 * c.f. JacksonAutoConfiguration, Jackson2ObjectMapperBuilder,
	 * AbstractJackson2HttpMessageConverter
	 */

	@Bean
	public Module jdk8Module() {
		return new Jdk8Module();
	}

	@Bean
	public Module javaTimeModule() {
		return new JavaTimeModule();
	}
}
