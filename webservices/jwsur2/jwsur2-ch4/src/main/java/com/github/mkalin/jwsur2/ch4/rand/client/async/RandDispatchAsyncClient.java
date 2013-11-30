package com.github.mkalin.jwsur2.ch4.rand.client.async;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.ws.AsyncHandler;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Response;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import com.github.mkalin.jwsur2.ch4.rand.client.async.generated.NextN;
import com.github.mkalin.jwsur2.ch4.rand.client.async.generated.ObjectFactory;

public class RandDispatchAsyncClient {
	private static final String ENDPOINT_URL = "http://localhost:8080/ch4/rand";
	private static final String SERVICE_NAME = "RandServiceService";
	private static final String PORT_NAME = "RandServicePort";
	private static final String NAMESPACE_URI = "http://rand.ch4.jwsur2.mkalin.github.com/";
	private static final QName serviceName = new QName(NAMESPACE_URI,
			SERVICE_NAME);
	private static final QName portName = new QName(NAMESPACE_URI, PORT_NAME);

	private static final Dispatch<Object> dispatch;

	static {
		/** Create a service and add at least one port to it. **/
		Service service = Service.create(serviceName);
		service.addPort(portName, SOAPBinding.SOAP11HTTP_BINDING, ENDPOINT_URL);

		JAXBContext jaxbContext = null;

		try {
			jaxbContext = JAXBContext
					.newInstance("com.github.mkalin.jwsur2.ch4.rand.client.async.generated");
		} catch (JAXBException e) {
			e.printStackTrace();
		}

		dispatch = service.createDispatch(portName, jaxbContext,
				Service.Mode.PAYLOAD);
	}

	public static void main(String[] args) {
		RandDispatchAsyncClient asyncClient = new RandDispatchAsyncClient();

		asyncClient.requestAndRegister();
	}

	/*
	 * This is a request-and-register style async invocation that registers a
	 * callback handler
	 */
	private void requestAndRegister() {
		// Note that the invocation is made with NextN wrapped in a JAXBElement;
		// that's how the response is going to be as well;
		// Response<JAXBElement<NextNResponse>>
		dispatch.invokeAsync(nextN(4), new RequestAndRegisterHandler());

		System.out.println("requestAndRegister is back...\n");

		try {
			Thread.sleep(5000); // in production, do something useful!
		} catch (Exception e) {
		}

		System.out.println("\nrequestAndRegister is exiting...");
	}

	private JAXBElement<NextN> nextN(int i) {
		ObjectFactory objFactory = new ObjectFactory();
		NextN nextN = objFactory.createNextN();
		nextN.setArg0(i);

		return objFactory.createNextN(nextN);
	}

	private final class RequestAndRegisterHandler implements
			AsyncHandler<Object> {
		public void handleResponse(Response<Object> future) {
			System.out.println("Handler is called...");

			try {
				processResponse(future.get());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void processResponse(Object obj) {
		@SuppressWarnings("unchecked")
		JAXBElement<NextNResponse> response = (JAXBElement<NextNResponse>) obj;
		List<Integer> nums = response.getValue().getReturn();

		for (Integer num : nums) {
			System.out.println(num);
		}
	}
}
