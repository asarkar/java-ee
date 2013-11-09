package edu.certification.abhijitsarkar.ocewsd.jaxrs.webapp.client;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.representation.Form;

import edu.certification.abhijitsarkar.ocewsd.utility.logging.AppLogger;

public class CarResourceJerseyClient {

	public CarResourceJerseyClient() {
		cc = new DefaultClientConfig();
		c = Client.create(cc);
		r = c.resource(RESOURCE_URI);
	}

	public String sendBasicPathParamRequest(String uri) {
		WebResource r1 = r.path(uri);
		String make = r1.get(String.class);

		logger.debug("URI: " + uri + ", Make: " + make);

		return make;
	}

	public String sendMultiplePathParamsRequest(String uri) {
		WebResource r1 = r.path(uri);
		String response = r1.get(String.class);

		logger.debug("URI: " + uri + ", Response: " + response);

		return response;
	}

	public void sendMultiplePathSegmentsRequest(String uri) {
		WebResource r1 = r.path(uri);
		r1.post();

		logger.debug("URI: " + uri);
	}

	public String sendMatrixParamRequest(String uri) {
		WebResource r1 = r.path(uri);
		String response = r1.get(String.class);

		logger.debug("URI: " + uri + ", Response: " + response);

		return response;
	}

	public String sendQueryParamRequest(String uri, String queryParamKey,
			String queryParamVal) {
		WebResource r1 = r.path(uri);
		r1 = r1.queryParam(queryParamKey, queryParamVal);
		String response = r1.get(String.class);

		logger.debug("URI: " + uri + ", Response: " + response);

		return response;
	}

	public String sendCookieParamRequest(String uri, String cookieName,
			String cookieVal) {
		WebResource r1 = r.path(uri);
		String response = r1.cookie(new Cookie(cookieName, cookieVal)).get(
				String.class);

		logger.debug("URI: " + uri + ", Response: " + response);

		return response;
	}

	public String sendHeaderParamRequest(String uri, String headerName,
			String headerVal) {
		WebResource r1 = r.path(uri);
		String response = r1.header(headerName, headerVal).get(String.class);

		logger.debug("URI: " + uri + ", Response: " + response);

		return response;
	}

	public String sendFormParamRequest(String uri, String formParamKey,
			String formParamVal) {
		Form f = new Form();
		f.add(formParamKey, formParamVal);

		WebResource r1 = r.path(uri);

		/* Seems like forms must be POSTed */
		String response = r1.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				.post(String.class, f);

		logger.debug("URI: " + uri + ", Response: " + response);

		return response;
	}

	public String sendContextRequest(String uri) {
		WebResource r1 = r.path(uri);
		String response = r1.get(String.class);

		logger.debug("URI: " + uri + ", Response: " + response);

		return response;
	}

	private final ClientConfig cc;
	private final Client c;
	private final WebResource r;
	private static final String RESOURCE_URI = "http://localhost:8080/jaxrs-basic-1.0/jaxrs-basic/car/";
	private static final Logger logger = AppLogger
			.getInstance(CarResourceJerseyClient.class);

}
