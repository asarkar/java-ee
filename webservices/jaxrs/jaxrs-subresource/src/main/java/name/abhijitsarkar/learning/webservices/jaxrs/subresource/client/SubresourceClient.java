package name.abhijitsarkar.learning.webservices.jaxrs.subresource.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;

public class SubresourceClient {
	private static final String CAR_RESOURCE_URI = "http://localhost:8080/jaxrs-subresource/car";

	private final Client client = ClientBuilder.newClient();

	public static void main(String[] args) {
		SubresourceClient srClient = new SubresourceClient();

		System.out.println("Path param request...");
		srClient.request(buildUri("/subresource/basicPathParam/Mercedes"));

		System.out.println("Multiple Path param request...");
		srClient.request(buildUri("/subresource/multiplePathParams/Mercedes-C250-2012"));

		System.out.println("Multiple Path segments request...");
		String uri = "/subresource/multiplePathSegments/Mercedes/C250;"
				+ "loaded=true/yes;moonroof/AMG/2012?"
				+ "make=Mercedes&model=C250";
		srClient.multiplePathSegmentsRequest(buildUri(uri));

		System.out.println("Matrix param request...");
		// Won't match if there're more than one matrix params
		srClient.request(buildUri("/subresource/matrixParam/C250/2012;color=black"));

		System.out.println("Query param request...");
		srClient.queryParamRequest(buildUri("/subresource/queryParam"), "make",
				"Mercedes");

		System.out.println("Cookie param request...");
		srClient.cookieParamRequest(buildUri("/subresource/cookieParam"),
				"make", "Mercedes");

		System.out.println("Header param request...");
		srClient.headerParamRequest(buildUri("/subresource/headerParam"),
				"make", "Mercedes");

		System.out.println("Form param request...");
		srClient.formParamRequest(buildUri("/subresource/formParam"), "make",
				"Mercedes");

		System.out.println("Context request 1...");
		srClient.request(buildUri("/subresource/ctx/Mercedes/C250"));

		System.out.println("Context request 2...");
		srClient.request(buildUri("/ctx/Mercedes/C250"));
	}

	private static String buildUri(String path) {
		return CAR_RESOURCE_URI + path;
	}

	public void multiplePathSegmentsRequest(String uri) {
		WebTarget wt = client.target(uri);
		Builder builder = wt.request();

		request(uri, "POST", builder);
	}

	public void queryParamRequest(String uri, String queryParamKey,
			String queryParamVal) {
		WebTarget wt = client.target(uri);
		Builder builder = wt.queryParam(queryParamKey, queryParamVal).request();

		request(uri, builder);
	}

	public void cookieParamRequest(String uri, String cookieName,
			String cookieVal) {
		WebTarget wt = client.target(uri);
		Builder builder = wt.request().cookie(cookieName, cookieVal);

		request(uri, builder);
	}

	public void headerParamRequest(String uri, String headerName,
			String headerVal) {
		WebTarget wt = client.target(uri);
		Builder builder = wt.request().header(headerName, headerVal);

		request(uri, builder);
	}

	public void formParamRequest(String uri, String formParamKey,
			String formParamVal) {
		Form f = new Form();
		f.param(formParamKey, formParamVal);

		WebTarget wt = client.target(uri);

		/* Form must be POSTed */
		String response = wt.request(MediaType.APPLICATION_JSON).post(
				Entity.form(f), String.class);

		System.out.printf("URI: %s, Response: %s\n\n", uri, response);
	}

	private void request(String uri) {
		WebTarget wt = client.target(uri);
		Builder builder = wt.request();

		request(uri, builder);
	}

	private void request(String uri, Builder builder) {
		request(uri, "GET", builder);
	}

	private void request(String uri, String method, Builder builder) {
		if ("GET".equals(method)) {
			String response = builder.accept(MediaType.APPLICATION_JSON).get(
					String.class);

			System.out.printf("URI: %s, Response: %s\n\n", uri, response);
		} else if ("POST".equals(method)) {
			// The Entity class encapsulates the Java object we want to send
			// with the POST request
			String response = builder.accept(MediaType.APPLICATION_JSON).post(
					Entity.text("POST request"), String.class);

			System.out.printf("URI: %s, Response: %s\n\n", uri, response);
		}
	}
}
