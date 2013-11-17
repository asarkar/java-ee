package name.abhijitsarkar.learning.webservices.jaxws.security.decl.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class CalculatorDeclClient {

	private static final String CALC_RESOURCE_URI = "http://localhost:8080/calc-decl/";

	private static Client client = ClientBuilder.newClient();

	static {
		client = client.register(new HttpBasicAuthFilter("bob", "password"));
	}

	public static void main(String[] args) {
		CalculatorDeclClient cClient = new CalculatorDeclClient();
		cClient.request(MediaType.APPLICATION_JSON, "add", 2, 3);
		cClient.request(MediaType.APPLICATION_JSON, "subtract", 5, 3);

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
