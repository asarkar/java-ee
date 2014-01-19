package name.abhijitsarkar.webservices.jaxws.instrumentation.config;

import javax.annotation.PostConstruct;

import org.apache.cxf.Bus;
import org.apache.cxf.management.InstrumentationManager;
import org.apache.cxf.management.counters.CounterRepository;
import org.apache.cxf.management.jmx.InstrumentationManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.util.Assert;

@ConditionalOnJAXWSEngine("cxf")
@Configuration
@ImportResource({ "classpath:META-INF/cxf/cxf.xml" })
public class CXFConfig {
	@Autowired
	Bus cxfBus;

	@PostConstruct
	public void postConstruct() {
		Assert.notNull(cxfBus, "CXF bus must not be null.");
	}

	// @Bean
	// public Endpoint calculator() {
	// EndpointImpl endpoint = new EndpointImpl(cxfBus, new Calculator());
	// endpoint.setAddress("/CalculatorService");
	// return endpoint;
	// }

	@Bean
	public InstrumentationManager instrumentationManager() {
		InstrumentationManagerImpl instrumentationManager = new InstrumentationManagerImpl();
		instrumentationManager.setEnabled(true);

		instrumentationManager.setBus(cxfBus);
		instrumentationManager.setUsePlatformMBeanServer(true);

		return instrumentationManager;
	}

	@Bean
	public CounterRepository CounterRepository() {
		CounterRepository counterRepository = new CounterRepository();

		counterRepository.setBus(cxfBus);

		return counterRepository;
	}
}
