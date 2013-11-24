package name.abhijitsarkar.webservices.jaxws.deploydesc.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;
import javax.xml.ws.soap.SOAPBinding;

import name.abhijitsarkar.webservices.jaxws.handler.SOAPMessageLoggingHandler;

public class CalculatorClient {
	private static final String ENDPOINT_URL = "http://localhost:8080/jaxws-deploy-desc/CalculatorService";
	private static final String SERVICE_NAME = "JAXWSProviderService";
	private static final String PORT_NAME = "Calculator";
	private static final String NAMESPACE_URI = "http://deploydesc.jaxws.webservices.abhijitsarkar.name/";
	// The invocation works even if the QName is wrong; wonder how
	private static final QName serviceName = new QName(
			"http://abhijitsarkar.name/webservices/jaxws/deploy-desc/",
			SERVICE_NAME);
	private static final QName portName = new QName(NAMESPACE_URI, PORT_NAME);

	private static Dispatch<SOAPMessage> dispatch = null;

	static {
		/** Create a service and add at least one port to it. **/
		Service service = Service.create(serviceName);
		service.setHandlerResolver(new HandlerResolver() {

			@SuppressWarnings("rawtypes")
			@Override
			public List<Handler> getHandlerChain(PortInfo portInfo) {
				List<Handler> handlerChain = new ArrayList<Handler>();
				handlerChain.add(new SOAPMessageLoggingHandler());

				return handlerChain;
			}
		});
		service.addPort(portName, SOAPBinding.SOAP11HTTP_BINDING, ENDPOINT_URL);
		// The mode must be message if the entity type is SOAPMessage
		dispatch = service.createDispatch(portName, SOAPMessage.class,
				Service.Mode.MESSAGE);
	}

	public static void main(String[] args) {
		int i = 1;
		int j = 2;
		int sum = 0;

		/** Create SOAPMessage request. **/
		try {
			MessageFactory mf = MessageFactory
					.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
			// Create a message.
			SOAPMessage request = mf.createMessage();

			// Obtain the SOAP body.
			SOAPBody body = request.getSOAPBody();

			// Construct the message payload.
			SOAPElement operation = body.addChildElement("add", "ns",
					NAMESPACE_URI);
			SOAPElement arg0 = operation.addChildElement("arg0");
			arg0.addTextNode(Integer.toString(i));
			SOAPElement arg1 = operation.addChildElement("arg1");
			arg1.addTextNode(Integer.toString(j));
			request.saveChanges();

			/** Invoke the service endpoint. **/
			SOAPMessage response = dispatch.invoke(request);

			/** Process the response. **/
			body = response.getSOAPBody();
			@SuppressWarnings("unchecked")
			Iterator<SOAPElement> it = body.getChildElements(new QName(
					NAMESPACE_URI, "addResponse"));

			sum = Integer.valueOf(it.next().getTextContent());
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Sum of " + i + " and " + j + " is " + sum);
	}
}
