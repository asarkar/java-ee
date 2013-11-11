package name.abhijitsarkar.learning.webservices.jaxws.addressing.client;

import name.abhijitsarkar.learning.webservices.jaxws.addressing.client.generated.CalculatorAddrService;

public class AddressingClient {

	public static void main(String[] args) {
		CalculatorAddrService service = new CalculatorAddrService();
		System.out.println(service.getCalculatorAddrPort().add(1, 2));
	}
}
