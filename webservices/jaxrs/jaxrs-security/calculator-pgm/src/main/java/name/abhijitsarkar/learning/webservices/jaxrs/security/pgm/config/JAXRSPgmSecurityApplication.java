package name.abhijitsarkar.learning.webservices.jaxrs.security.pgm.config;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import name.abhijitsarkar.learning.webservices.jaxrs.security.pgm.CalculatorEJB;
import name.abhijitsarkar.learning.webservices.jaxrs.security.pgm.auth.MustBeCelebrityFeature;

public class JAXRSPgmSecurityApplication extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<Class<?>>(super.getClasses());
		classes.add(CalculatorEJB.class);

		// Register DynamicFeature to enable programmatic security
		classes.add(MustBeCelebrityFeature.class);

		return classes;
	}

	@Override
	public Set<Object> getSingletons() {
		Set<Object> singletons = new HashSet<Object>(super.getSingletons());
		return singletons;
	}
}
