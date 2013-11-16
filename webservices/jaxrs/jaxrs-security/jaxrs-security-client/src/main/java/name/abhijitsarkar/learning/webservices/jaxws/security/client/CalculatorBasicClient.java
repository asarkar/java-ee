package name.abhijitsarkar.learning.webservices.jaxws.security.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class CalculatorBasicClient {

	private static final String CALC_RESOURCE_URI = "http://localhost:8080/calc-basic/";

	private static Client client = ClientBuilder.newClient();

	static {
		client = client.register(new BasicAuthHdrAppender("bob", "password"));
	}

	public static void main(String[] args) {
		CalculatorBasicClient cClient = new CalculatorBasicClient();
		cClient.request(MediaType.APPLICATION_JSON, 2, 3);

		client.close();
	}

	private void request(String mediaType, int arg0, int arg1) {
		WebTarget wt = client.target(CALC_RESOURCE_URI);
		Builder builder = wt.path("add").queryParam("arg0", arg0)
				.queryParam("arg1", arg1).request();

		Integer response = builder.accept(mediaType).get(int.class);

		System.out.println("Response: " + response);
	}
}
