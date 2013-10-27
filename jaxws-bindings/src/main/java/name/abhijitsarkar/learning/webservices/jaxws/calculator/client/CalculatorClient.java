package name.abhijitsarkar.learning.webservices.jaxws.calculator.client;

import name.abhijitsarkar.learning.webservices.jaxws.calculator.AddRequest;
import name.abhijitsarkar.learning.webservices.jaxws.calculator.AddResponse;
import name.abhijitsarkar.learning.webservices.jaxws.calculator.Calculator;
import name.abhijitsarkar.learning.webservices.jaxws.calculator.SubtractRequest;
import name.abhijitsarkar.learning.webservices.jaxws.calculator.SubtractResponse;

public class CalculatorClient {
	private Calculator calculator = null;

	public CalculatorClient() {
		calculator = new CalculatorService().getCalculatorPort();
	}

	public static void main(String[] args) {
		CalculatorClient client = new CalculatorClient();

		client.add();
		client.subtract();
	}

	private void add() {
		AddRequest addRequest = new AddRequest();
		addRequest.setParam1(1.0f);
		addRequest.setParam2(2.0f);

		AddResponse sum = calculator.add(addRequest);

		System.out.println("Sum: " + sum.getResult());
	}

	private void subtract() {
		SubtractRequest subtractRequest = new SubtractRequest();
		subtractRequest.setParam1(2.0f);
		subtractRequest.setParam2(1.0f);

		SubtractResponse difference = calculator.subtract(subtractRequest);

		System.out.println("Difference: " + difference.getResult());
	}
}
