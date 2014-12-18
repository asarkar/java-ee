package name.abhijitsarkar.learning.webservices.jaxrs.fileprovider;

import static javax.ws.rs.core.UriBuilder.fromUri;
import static org.glassfish.jersey.jdkhttp.JdkHttpServerFactory.createHttpServer;
import static org.glassfish.jersey.server.ResourceConfig.forApplicationClass;

import java.net.URI;

import org.glassfish.jersey.server.ResourceConfig;

public class Driver {
	public static void main(String[] args) {
		final URI baseUri = fromUri("http://localhost/").port(9998).build();
		final ResourceConfig config = forApplicationClass(AppConfig.class);

		createHttpServer(baseUri, config);
	}
}
