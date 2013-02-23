package edu.certification.abhijitsarkar.ocewsd.jaxrs.ejb.config;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import edu.certification.abhijitsarkar.ocewsd.jaxrs.ejb.CarResource;


@ApplicationPath("jaxrs-ejb")
public class CarApplication extends Application {
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<Class<?>>(super.getClasses());
		classes.add(CarResource.class);

		return classes;
	}

	@Override
	public Set<Object> getSingletons() {
		return super.getSingletons();
	}
}