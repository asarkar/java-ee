package name.abhijitsarkar.webservices.jaxws.instrumentation.config;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.cxf.Bus;
import org.apache.cxf.management.InstrumentationManager;
import org.apache.cxf.management.counters.CounterRepository;
import org.apache.cxf.management.jmx.InstrumentationManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;
import org.springframework.util.Assert;

@Profile("cxf")
@Configuration
@ImportResource({ "classpath:META-INF/cxf/cxf.xml" })
public class CXFJMXConfig {
	@Autowired
	Bus cxfBus;

	@PostConstruct
	public void postConstruct() {
		Assert.notNull(cxfBus, "CXF bus must not be null.");

		// cxfBus.setProperties(busProperties());
	}

	@Bean
	InstrumentationManager instrumentationManager() {
		InstrumentationManagerImpl instrumentationManager = new InstrumentationManagerImpl();
		instrumentationManager.setEnabled(true);

		instrumentationManager.setBus(cxfBus);
		instrumentationManager.setUsePlatformMBeanServer(true);
//		instrumentationManager.setJMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:9090/jmxrmi");

		return instrumentationManager;
	}

	private Map<String, Object> busProperties() {
		Map<String, Object> busProperties = new HashMap<>();
		busProperties.put("jmx.enabled", true);
		busProperties.put("jmx.JMXServiceURL",
				"service:jmx:rmi:///jndi/rmi://localhost:9090/jmxrmi");

		return busProperties;
	}

	@Bean
	CounterRepository CounterRepository() {
		CounterRepository counterRepository = new CounterRepository();

		counterRepository.setBus(cxfBus);

		return counterRepository;
	}
}
