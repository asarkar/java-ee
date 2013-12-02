package name.abhijitsarkar.webservices.jaxws.exception.client;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;
import javax.xml.ws.soap.SOAPFaultException;

import name.abhijitsarkar.webservices.jaxws.exception.client.generated.Divide;
import name.abhijitsarkar.webservices.jaxws.exception.client.generated.DivideResponse;
import name.abhijitsarkar.webservices.jaxws.exception.client.generated.Multiply;
import name.abhijitsarkar.webservices.jaxws.exception.client.generated.MultiplyResponse;
import name.abhijitsarkar.webservices.jaxws.exception.client.generated.ObjectFactory;

public class CalculatorDispatchClient {
	private static final String ENDPOINT_URL = "http://localhost:8080/jaxws-exception/CalculatorService";
	private static final String NAMESPACE_URI = "http://exception.jaxws.webservices.abhijitsarkar.name/";
	private static final String SERVICE_NAME = "CalculatorService";
	private static final String PORT_NAME = "CalculatorPort";

	private final Dispatch<Object> dispatch;

	private CalculatorDispatchClient() throws JAXBException {
		/** Create a service and add at least one port to it. **/
		Service service = createServiceWithPort();

		JAXBContext jaxbCtx = JAXBContext
				.newInstance("name.abhijitsarkar.webservices.jaxws.exception.client.generated");

		/** Create a Dispatch instance from a service. **/
		dispatch = service.createDispatch(new QName(NAMESPACE_URI, PORT_NAME),
				jaxbCtx, Service.Mode.PAYLOAD);

	}

	private Service createServiceWithPort() {
		Service service = Service
				.create(new QName(NAMESPACE_URI, SERVICE_NAME));
		service.addPort(new QName(NAMESPACE_URI, PORT_NAME),
				SOAPBinding.SOAP11HTTP_BINDING, ENDPOINT_URL);

		return service;
	}

	public static void main(String[] args) throws JAXBException {
		CalculatorDispatchClient client = new CalculatorDispatchClient();

		client.multiply(1, 2);
		client.divide(1, 2);
		client.divide(1, 0);
		client.divide(1, -2);
	}

	private void multiply(int i, int j) throws JAXBException {
		Multiply multiply = new Multiply();
		multiply.setArg0(i);
		multiply.setArg1(j);

		ObjectFactory objFactory = new ObjectFactory();

		int product = invoke(objFactory.createMultiply(multiply),
				MultiplyResponse.class).getReturn();

		System.out.println(i + " * " + j + " = " + product);
	}

	private void divide(int i, int j) throws JAXBException {
		Divide divide = new Divide();
		divide.setArg0(i);
		divide.setArg1(j);

		ObjectFactory objFactory = new ObjectFactory();

		int quotient = 0;
		try {
			quotient = invoke(objFactory.createDivide(divide),
					DivideResponse.class).getReturn();
		} catch (JAXBException e) {
			throw e;
		}
		// This is for the divide exceptions
		catch (SOAPFaultException e) {
			e.printStackTrace();
		}

		System.out.println(i + " / " + j + " = " + quotient);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private <T> T invoke(Object request, Class<T> responseClass)
			throws JAXBException {
		Object response = dispatch.invoke(request);

		if (response instanceof JAXBElement) {
			return (T) ((JAXBElement) response).getValue();
		}

		return null;
	}
}
