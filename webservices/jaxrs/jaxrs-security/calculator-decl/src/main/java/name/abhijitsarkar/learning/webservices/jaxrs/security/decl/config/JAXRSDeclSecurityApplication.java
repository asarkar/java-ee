package name.abhijitsarkar.learning.webservices.jaxrs.security.decl.config;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import name.abhijitsarkar.learning.webservices.jaxrs.security.decl.Calculator;

import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

public class JAXRSDeclSecurityApplication extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<Class<?>>(super.getClasses());
		classes.add(Calculator.class);
		// Register Jersey DynamicFeature to enable annotation-based security
		classes.add(RolesAllowedDynamicFeature.class);

		return classes;
	}

	@Override
	public Set<Object> getSingletons() {
		Set<Object> singletons = new HashSet<Object>(super.getSingletons());
		return singletons;
	}
}
