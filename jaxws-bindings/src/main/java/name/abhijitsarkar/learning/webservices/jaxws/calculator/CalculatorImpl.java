package name.abhijitsarkar.learning.webservices.jaxws.calculator;

import javax.jws.WebService;

@WebService(endpointInterface = "name.abhijitsarkar.learning.webservices.jaxws.calculator.Calculator", 
  targetNamespace = "http://abhijitsarkar.name/learning/webservices/jaxws/calculator/", 
  serviceName = "CalculatorService", portName = "CalculatorPort")
public class CalculatorImpl implements Calculator {

	@Override
	public AddResponse add(AddRequest parameters) {
		float param1 = parameters.getParam1();
		float param2 = parameters.getParam2();

		float sum = param1 + param2;

		AddResponse response = new AddResponse();
		response.setResult(sum);

		return response;
	}

	@Override
	public SubtractResponse subtract(SubtractRequest parameters) {
		float param1 = parameters.getParam1();
		float param2 = parameters.getParam2();

		float difference = param1 - param2;

		SubtractResponse response = new SubtractResponse();
		response.setResult(difference);

		return response;
	}
}
