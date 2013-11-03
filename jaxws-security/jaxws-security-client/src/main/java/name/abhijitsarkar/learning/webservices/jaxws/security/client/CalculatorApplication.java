package name.abhijitsarkar.learning.webservices.jaxws.security.client;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class CalculatorApplication extends Application {
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> s = new HashSet<Class<?>>();
		s.add(CalculatorResource.class);
		return s;
	}
}
