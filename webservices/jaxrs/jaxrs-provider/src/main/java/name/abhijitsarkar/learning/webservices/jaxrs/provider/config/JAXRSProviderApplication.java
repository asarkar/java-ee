package name.abhijitsarkar.learning.webservices.jaxrs.provider.config;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import name.abhijitsarkar.learning.webservices.jaxrs.provider.CarsMarshaler;
import name.abhijitsarkar.learning.webservices.jaxrs.provider.XMLMediaTypeAppender;
import name.abhijitsarkar.learning.webservices.jaxrs.provider.client.OriginalAcceptHdrRestorer;
import name.abhijitsarkar.learning.webservices.jaxrs.provider.resource.Calculator;
import name.abhijitsarkar.learning.webservices.jaxrs.provider.resource.CarDealer;

@ApplicationPath("/")
public class JAXRSProviderApplication extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<Class<?>>(super.getClasses());
		classes.add(Calculator.class);
		classes.add(CarDealer.class);
		return classes;
	}

	@Override
	public Set<Object> getSingletons() {
		Set<Object> objects = new HashSet<Object>(super.getSingletons());
		objects.add(new CarsMarshaler());
		objects.add(new XMLMediaTypeAppender());
		objects.add(new OriginalAcceptHdrRestorer());

		return objects;
	}
}
