package name.abhijitsarkar.learning.webservices.jaxrs.content.client;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import name.abhijitsarkar.learning.webservices.jaxrs.content.domain.Car;
import name.abhijitsarkar.learning.webservices.jaxrs.content.provider.CarsUnmarshaler;

public class CarDealerClient {
	private static final String DEALER_RESOURCE_URI = "http://localhost:8080/jaxrs-content/dealer";

	private static final Client client;

	static {
		client = ClientBuilder.newClient().register(CarsUnmarshaler.class);
	}

	public static void main(String[] args) {
		CarDealerClient dealerClient = new CarDealerClient();
		dealerClient.request(MediaType.APPLICATION_JSON);

		dealerClient.request(MediaType.APPLICATION_XML);

		client.close();
	}

	private void request(String mediaType) {
		WebTarget wt = client.target(DEALER_RESOURCE_URI);
		Builder builder = wt.request();

		GenericType<List<Car>> cars = new GenericType<List<Car>>() {
		};

		List<Car> response = builder.accept(mediaType).get(cars);

		System.out.println("Response: " + response);
	}
}
