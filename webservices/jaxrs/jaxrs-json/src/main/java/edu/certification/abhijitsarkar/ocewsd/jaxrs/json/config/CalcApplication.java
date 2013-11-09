package edu.certification.abhijitsarkar.ocewsd.jaxrs.json.config;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import edu.certification.abhijitsarkar.ocewsd.jaxrs.json.Calculator;

/*
 *  For simple deployments, no web.xml is needed at all.
 *  Instead, an @ApplicationPath annotation can be used to annotate 
 *  the user defined application class and specify 
 *  the base resource URI of all application resources. Packages are separated
 *  by semi-colons (;).
 *  
 *   @ApplicationPath("jaxrs-json")
 *   public class CalcApplication extends PackagesResourceConfig {
 *   	public MyApplication() {
 *   		super("edu.certification.abhijitsarkar.ocewsd.jaxrs.json");
 *   	}
 *   	...
 *   }
 *   
 *   For complex deployments, e.g. when a security model needs to be defined,
 *   a web.xml is needed. We use a web.xml in this project.
 *   
 */
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
