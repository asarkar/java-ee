package name.abhijitsarkar.webservices.jaxws.provider.client;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.soap.SOAPBinding;

import org.w3c.dom.Document;

public class JAXWSProviderDispatchClient {

	public static void main(String[] args) {
		JAXWSProviderDispatchClient client = new JAXWSProviderDispatchClient();

		client.invokeAdd1(1, 2);
		client.invokeAdd2(1, 2);
		client.invokeSubtract1(3, 2);
		client.invokeException(0, 0);
	}

	private JAXWSProviderDispatchClient() {
		/** Create a service and add at least one port to it. **/
		Service service = Service.create(serviceName);
		service.setHandlerResolver(new JAXWSProviderClientHandlerResolver());
		service.addPort(portName, SOAPBinding.SOAP11HTTP_BINDING, ENDPOINT_URL);

		/** Create a Dispatch instance from a service. **/
		dispatch = service.createDispatch(portName, SOAPMessage.class,
				Service.Mode.MESSAGE);

		// BindingProvider provider = (BindingProvider) dispatch;

		// Map<String, Object> reqCtx = provider.getRequestContext();

		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("Content-Type", Collections.singletonList("text/xml"));
		headers.put("Accept", Collections.singletonList("text/xml"));

		Map<String, Object> reqCtx = dispatch.getRequestContext();
		reqCtx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
	}

	// Invoke add using one style of SAAJ API
	private void invokeAdd1(int i, int j) {
		int sum = 0;

		/** Create SOAPMessage request. **/
		try {
			MessageFactory mf = MessageFactory
					.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
			// Create a message.
			SOAPMessage request = mf.createMessage();

			addOperationHdr("add");

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
					NAMESPACE_URI, "result"));

			sum = Integer.valueOf(it.next().getTextContent());
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Sum of " + i + " and " + j + " is " + sum);
	}

	// Invoke add using a different style of SAAJ API, specifically how the SOAP
	// body is obtained and added to the message
	private void invokeAdd2(int i, int j) {
		int sum = 0;

		/** Create SOAPMessage request. **/
		try {
			MessageFactory mf = MessageFactory
					.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
			// Create a message.
			SOAPMessage request = mf.createMessage();

			addOperationHdr("add");

			// Obtain the SOAP body from envelope.
			SOAPEnvelope soapEnv = request.getSOAPPart().getEnvelope();

			Name bodyName = soapEnv.createName("add", "ns", NAMESPACE_URI);
			SOAPBody body = soapEnv.getBody();
			SOAPBodyElement bodyElement = body.addBodyElement(bodyName);

			// Construct the message payload.
			SOAPElement arg0 = bodyElement.addChildElement("arg0");
			arg0.addTextNode(Integer.toString(i));
			SOAPElement arg1 = bodyElement.addChildElement("arg1");
			arg1.addTextNode(Integer.toString(j));
			request.saveChanges();

			/** Invoke the service endpoint. **/
			SOAPMessage response = dispatch.invoke(request);

			/** Process the response. **/
			body = response.getSOAPBody();
			@SuppressWarnings("unchecked")
			Iterator<SOAPElement> it = body.getChildElements(new QName(
					NAMESPACE_URI, "result"));

			sum = Integer.valueOf(it.next().getTextContent());
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Sum of " + i + " and " + j + " is " + sum);
	}

	// This method uses JAXB to construct the message
	private void invokeSubtract1(int i, int j) {
		int diff = 0;

		try {
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory
					.newInstance();
			builderFactory.setNamespaceAware(true);
			Document doc = builderFactory.newDocumentBuilder().newDocument();

			SubtractRequest subtractRequest = new SubtractRequest(i, j);

			JAXBContext context = JAXBContext.newInstance(
					SubtractRequest.class, SubtractResponse.class);

			context.createMarshaller().marshal(subtractRequest, doc);

			MessageFactory mf = MessageFactory
					.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
			// Create a message.
			SOAPMessage request = mf.createMessage();

			addOperationHdr("subtract");

			// Obtain the body element.
			SOAPBody body = request.getSOAPBody();

			body.addDocument(doc);

			SOAPMessage response = dispatch.invoke(request);

			SubtractResponse subtractResponse = context
					.createUnmarshaller()
					.unmarshal(response.getSOAPBody().getFirstChild(),
							SubtractResponse.class).getValue();

			diff = subtractResponse.getResult();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Difference of " + i + " and " + j + " is " + diff);
	}

	private void invokeException(int i, int j) {
		/** Create SOAPMessage request. **/
		try {
			MessageFactory mf = MessageFactory
					.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
			// Create a message.
			SOAPMessage request = mf.createMessage();

			// "multiply" isn't supported so it'll throw an exception
			addOperationHdr("multiply");

			// Obtain the SOAPEnvelope and header and body elements.
			SOAPBody body = request.getSOAPBody();

			// Construct the message payload.
			SOAPElement operation = body.addChildElement("multiply", "ns",
					NAMESPACE_URI);
			SOAPElement arg0 = operation.addChildElement("arg0");
			arg0.addTextNode(Integer.toString(i));
			SOAPElement arg1 = operation.addChildElement("arg1");
			arg1.addTextNode(Integer.toString(j));
			request.saveChanges();

			/** Invoke the service endpoint **/
			dispatch.invoke(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addOperationHdr(String opName) {
		Map<String, Object> reqCtx = dispatch.getRequestContext();

		@SuppressWarnings("unchecked")
		Map<String, Object> reqHeaders = (Map<String, Object>) reqCtx
				.get(MessageContext.HTTP_REQUEST_HEADERS);
		reqHeaders.put("operation", Collections.singletonList(opName));

		reqCtx.put(MessageContext.HTTP_REQUEST_HEADERS, reqHeaders);
	}

	private static final String ENDPOINT_URL = "http://localhost:8080/jaxws-provider/";
	private static final String NAMESPACE_URI = "http://abhijitsarkar.name/webservices/jaxws/provider/";
	private static final String SERVICE_NAME = "JAXWSProviderService";
	private static final String PORT_NAME = "JAXWSProvider";
	private static final QName serviceName = new QName(NAMESPACE_URI,
			SERVICE_NAME);
	private static final QName portName = new QName(NAMESPACE_URI, PORT_NAME);

	private Dispatch<SOAPMessage> dispatch = null;
}
