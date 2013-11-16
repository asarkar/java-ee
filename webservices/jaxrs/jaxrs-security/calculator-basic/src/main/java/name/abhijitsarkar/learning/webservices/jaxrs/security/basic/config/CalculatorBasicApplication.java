package name.abhijitsarkar.learning.webservices.jaxrs.security.basic.config;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import name.abhijitsarkar.learning.webservices.jaxrs.security.basic.CalculatorBasic;

public class CalculatorBasicApplication extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<Class<?>>(super.getClasses());
		classes.add(CalculatorBasic.class);

		return classes;
	}

	@Override
	public Set<Object> getSingletons() {
		return super.getSingletons();
	}
}
