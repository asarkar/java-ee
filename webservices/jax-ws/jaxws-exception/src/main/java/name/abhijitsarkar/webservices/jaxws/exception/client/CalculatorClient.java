package name.abhijitsarkar.webservices.jaxws.exception.client;

import name.abhijitsarkar.webservices.jaxws.exception.client.generated.Calculator;
import name.abhijitsarkar.webservices.jaxws.exception.client.generated.CalculatorService;
import name.abhijitsarkar.webservices.jaxws.exception.client.generated.DivisionByZeroException;

public class CalculatorClient {
	private final Calculator calc;

	public CalculatorClient() {
		CalculatorService service = new CalculatorService();
		calc = service.getCalculatorPort();
	}

	public static void main(String[] args) {
		CalculatorClient client = new CalculatorClient();

		client.multiply(1, 2);
		client.divide(1, 2);
		client.divide(1, 0);
		client.divide(1, -2);
	}

	private void multiply(int i, int j) {
		int product = calc.multiply(i, j);

		System.out.println(i + " * " + j + " = " + product);
	}

	private void divide(int i, int j) {
		int quotient = 0;
		try {
			quotient = calc.divide(i, j);
		} catch (DivisionByZeroException e) {
			e.printStackTrace();
		}

		System.out.println(i + " / " + j + " = " + quotient);
	}
}
