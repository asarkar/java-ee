package name.abhijitsarkar.webservices.jaxws.security.pgm.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import name.abhijitsarkar.webservices.jaxws.security.client.HttpBasicAuthFilter;

public class CalculatorPgmClient {

	private static final String CALC_RESOURCE_URI = "http://localhost:8080/calc-pgm/";

	private static Client client = ClientBuilder.newClient();

	static {
		client = client.register(new HttpBasicAuthFilter("bob", "password"));
	}

	public static void main(String[] args) {
		CalculatorPgmClient cClient = new CalculatorPgmClient();
		cClient.request(MediaType.APPLICATION_JSON, "add", 2, 3);

		client.close();
	}

	private void request(String mediaType, String operation, int arg0, int arg1) {
		WebTarget wt = client.target(CALC_RESOURCE_URI);
		Builder builder = wt.path(operation).queryParam("arg0", arg0)
				.queryParam("arg1", arg1).request();

		Integer response = builder.accept(mediaType).get(int.class);

		System.out.println("Response: " + response);
	}
}
