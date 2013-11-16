package name.abhijitsarkar.learning.webservices.jaxrs.provider.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import name.abhijitsarkar.learning.webservices.jaxrs.provider.domain.Car;
import name.abhijitsarkar.learning.webservices.jaxrs.provider.domain.Lamborghini;
import name.abhijitsarkar.learning.webservices.jaxrs.provider.domain.Porshe;

@Path("dealer")
public class CarDealer {
	private static final List<Car> cars;

	static {
		cars = new ArrayList<Car>();
		cars.add(new Porshe());
		cars.add(new Lamborghini());
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML })
	public List<Car> listAllCarsAsXML() {
		return cars;
	}
}
