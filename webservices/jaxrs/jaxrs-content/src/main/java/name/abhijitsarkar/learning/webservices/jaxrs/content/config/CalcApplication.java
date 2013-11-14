package name.abhijitsarkar.learning.webservices.jaxrs.content.config;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import name.abhijitsarkar.learning.webservices.jaxrs.content.Calculator;

@ApplicationPath("/")
public class CalcApplication extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<Class<?>>(super.getClasses());
		classes.add(Calculator.class);

		return classes;
	}

	@Override
	public Set<Object> getSingletons() {
		return super.getSingletons();
	}
}
