package name.abhijitsarkar.learning.webservices.jaxrs.fileprovider;

import static java.util.Arrays.asList;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/")
public class AppConfig extends Application {
	public Set<Class<?>> getClasses() {
		return new HashSet<Class<?>>(asList(FileProviderResource.class));
	}
}
