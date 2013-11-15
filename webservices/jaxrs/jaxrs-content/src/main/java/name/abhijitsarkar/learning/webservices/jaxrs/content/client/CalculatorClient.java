package name.abhijitsarkar.learning.webservices.jaxrs.content.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class CalculatorClient {
	private static final String CALC_RESOURCE_URI = "http://localhost:8080/jaxrs-content/calc";

	private static final Client client = ClientBuilder.newClient();

	public static void main(String[] args) {
		CalculatorClient cClient = new CalculatorClient();
		cClient.request(MediaType.APPLICATION_XML, 1, 2);
		cClient.request(MediaType.APPLICATION_JSON, 2, 3);

		client.close();
	}

	private void request(String mediaType, int arg0, int arg1) {
		WebTarget wt = client.target(CALC_RESOURCE_URI);
		Builder builder = wt.queryParam("arg0", arg0).queryParam("arg1", arg1)
				.request();

		String response = builder.accept(mediaType).get(String.class);

		System.out.println("Response: " + response);
	}
}
