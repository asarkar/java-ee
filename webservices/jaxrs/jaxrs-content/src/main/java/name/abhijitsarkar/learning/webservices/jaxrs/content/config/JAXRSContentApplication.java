package name.abhijitsarkar.learning.webservices.jaxrs.content.config;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import name.abhijitsarkar.learning.webservices.jaxrs.content.Calculator;
import name.abhijitsarkar.learning.webservices.jaxrs.content.CarDealer;
import name.abhijitsarkar.learning.webservices.jaxrs.content.provider.CarsMarshaler;

@ApplicationPath("/")
public class JAXRSContentApplication extends Application {

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

		return objects;
	}
}
