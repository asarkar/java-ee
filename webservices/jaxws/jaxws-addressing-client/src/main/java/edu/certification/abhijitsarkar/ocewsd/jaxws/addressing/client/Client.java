package edu.certification.abhijitsarkar.ocewsd.jaxws.addressing.client;

import edu.certification.abhijitsarkar.ocewsd.jaxws.addressing.CalculatorService;

public class Client {
	public int add(final int i, final int j) {
		CalculatorService service = new CalculatorService();
		return service.getCalculatorPort().add(i, j);
	}
}
