package name.abhijitsarkar.learning.webservices.jaxws.calculator.contractfirst.client;

import name.abhijitsarkar.learning.webservices.jaxws.calculator.contractfirst.client.generated.AddDefaultBareRequest;
import name.abhijitsarkar.learning.webservices.jaxws.calculator.contractfirst.client.generated.AddResponse;
import name.abhijitsarkar.learning.webservices.jaxws.calculator.contractfirst.client.generated.Calculator;
import name.abhijitsarkar.learning.webservices.jaxws.calculator.contractfirst.client.generated.CalculatorService;

public class CalculatorClient {
	private Calculator calculator = null;

	public CalculatorClient() {
		calculator = new CalculatorService().getCalculator();
	}

	public static void main(String[] args) {
		CalculatorClient client = new CalculatorClient();

		client.add();
		client.subtract();
	}

	private void add() {
		AddDefaultBareRequest addRequest = new AddDefaultBareRequest();
		addRequest.setParam1(1.0f);
		addRequest.setParam2(2.0f);

		AddResponse sum = calculator.addDefaultBare(addRequest);

		System.out.println("Sum: " + sum.getResult());
	}

	private void subtract() {
		float difference = calculator.subtractDefaultWrapped(2.0f, 1.0f);

		System.out.println("Difference: " + difference);
	}
}
