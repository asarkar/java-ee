package name.abhijitsarkar.webservices.jaxws.instrumentation.config;

import javax.annotation.PostConstruct;
import javax.xml.ws.Endpoint;

import name.abhijitsarkar.webservices.jaxws.instrumentation.Calculator;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.management.InstrumentationManager;
import org.apache.cxf.management.counters.CounterRepository;
import org.apache.cxf.management.jmx.InstrumentationManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.util.Assert;

@ConditionalOnCXFEngine
@Configuration
@ImportResource({ "classpath:META-INF/cxf/cxf.xml", "classpath:META-INF/cxf/cxf-servlet.xml" })
public class CXFConfig {
	@Autowired
	Bus cxfBus;

	@PostConstruct
	public void postConstruct() {
		Assert.notNull(cxfBus, "CXF bus must not be null.");

		// cxfBus.setProperties(busProperties());
	}

//	@Bean
//	public Endpoint calculator() {
//		EndpointImpl endpoint = new EndpointImpl(cxfBus, new Calculator());
//		endpoint.setAddress("/CalculatorService");
//		return endpoint;
//	}

	@Bean
	public InstrumentationManager instrumentationManager() {
		InstrumentationManagerImpl instrumentationManager = new InstrumentationManagerImpl();
		instrumentationManager.setEnabled(true);

		instrumentationManager.setBus(cxfBus);
		instrumentationManager.setUsePlatformMBeanServer(true);
		// instrumentationManager.setJMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:9090/jmxrmi");

		return instrumentationManager;
	}

	// private Map<String, Object> busProperties() {
	// Map<String, Object> busProperties = new HashMap<>();
	// busProperties.put("jmx.enabled", true);
	// busProperties.put("jmx.JMXServiceURL",
	// "service:jmx:rmi:///jndi/rmi://localhost:9090/jmxrmi");
	//
	// return busProperties;
	// }

	@Bean
	public CounterRepository CounterRepository() {
		CounterRepository counterRepository = new CounterRepository();

		counterRepository.setBus(cxfBus);

		return counterRepository;
	}
}
