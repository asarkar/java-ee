package name.abhijitsarkar.webservices.jaxws.calculator.contractfirst;

import javax.jws.WebService;

import name.abhijitsarkar.webservices.jaxws.calculator.contractfirst.generated.AddDefaultBareRequest;
import name.abhijitsarkar.webservices.jaxws.calculator.contractfirst.generated.AddResponse;
import name.abhijitsarkar.webservices.jaxws.calculator.contractfirst.generated.Calculator;

@WebService(endpointInterface = "name.abhijitsarkar.webservices.jaxws.calculator.contractfirst.generated.Calculator", targetNamespace = "http://abhijitsarkar.name/webservices/jaxws/calculator-cf/", serviceName = "CalculatorService", portName = "Calculator")
public class CalculatorImpl implements Calculator {

	@Override
	public AddResponse addDefaultBare(AddDefaultBareRequest parameters) {
		float param1 = parameters.getParam1();
		float param2 = parameters.getParam2();

		float sum = param1 + param2;

		AddResponse response = new AddResponse();
		response.setResult(sum);

		return response;
	}

	@Override
	public float subtractDefaultWrapped(float param1, float param2) {
		return param1 - param2;
	}
}
