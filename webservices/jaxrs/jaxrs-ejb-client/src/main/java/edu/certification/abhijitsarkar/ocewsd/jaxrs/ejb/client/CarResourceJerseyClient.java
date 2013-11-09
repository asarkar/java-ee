package edu.certification.abhijitsarkar.ocewsd.jaxrs.ejb.client;

import org.apache.log4j.Logger;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import edu.certification.abhijitsarkar.ocewsd.utility.logging.AppLogger;

public class CarResourceJerseyClient {

	public CarResourceJerseyClient() {
		cc = new DefaultClientConfig();
		c = Client.create(cc);
		r = c.resource(RESOURCE_URI);
	}

	public String sendRequest(String uri) {
		WebResource r1 = r.path(uri);
		String make = r1.get(String.class);

		logger.debug("URI: " + uri + ", Make: " + make);

		return make;
	}

	private final ClientConfig cc;
	private final Client c;
	private final WebResource r;
	private static final String RESOURCE_URI = "http://localhost:8080/jaxrs-ejb-1.0/jaxrs-ejb/car/";
	private static final Logger logger = AppLogger
			.getInstance(CarResourceJerseyClient.class);

}
