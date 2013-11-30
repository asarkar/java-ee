package name.abhijitsarkar.webservices.jaxws.provider.client;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.soap.SOAPBinding;
import javax.xml.ws.soap.SOAPFaultException;

import org.w3c.dom.Document;

public class JAXWSProviderDispatchClient {
	private static final String ENDPOINT_URL = "http://localhost:8080/jaxws-provider/";
	private static final String NAMESPACE_URI = "http://abhijitsarkar.name/webservices/jaxws/provider/";
	private static final String SERVICE_NAME = "JAXWSProviderService";
	private static final String PORT_NAME = "JAXWSProvider";
	private static final QName serviceName = new QName(NAMESPACE_URI,
			SERVICE_NAME);
	private static final QName portName = new QName(NAMESPACE_URI, PORT_NAME);

	private final Dispatch<SOAPMessage> dispatch;

	private JAXWSProviderDispatchClient() {
		/** Create a service and add at least one port to it. **/
		Service service = createServiceWithPort();

		/** Create a Dispatch instance from a service. **/
		dispatch = service.createDispatch(portName, SOAPMessage.class,
				Service.Mode.MESSAGE);

		/*
		 * Following shows 2 ways to get the request context map, from the
		 * BindingProvider and from the Dispatch
		 */

		// BindingProvider provider = (BindingProvider) dispatch;

		// Map<String, Object> reqCtx = provider.getRequestContext();

		// Map<String, Object> headers = new HashMap<String, Object>();
		// headers.put("Content-Type", Collections.singletonList("text/xml"));
		// headers.put("Accept", Collections.singletonList("text/xml"));
		//
		// Map<String, Object> reqCtx = dispatch.getRequestContext();
		// reqCtx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
	}

	private Service createServiceWithPort() {
		Service service = Service.create(serviceName);
		service.setHandlerResolver(new JAXWSProviderClientHandlerResolver());
		service.addPort(portName, SOAPBinding.SOAP11HTTP_BINDING, ENDPOINT_URL);

		return service;
	}

	public static void main(String[] args) {
		JAXWSProviderDispatchClient client = new JAXWSProviderDispatchClient();

		client.invokeSubtract1(3, 2);
		client.invokeException(0, 0);
		client.invokeAdd1(1, 2);
		client.invokeAdd2(1, 2);
	}

	// This method uses JAXB to construct the message
	private void invokeSubtract1(int i, int j) {
		int diff = 0;

		try {
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory
					.newInstance();
			builderFactory.setNamespaceAware(true);
			Document doc = builderFactory.newDocumentBuilder().newDocument();

			OperationRequest operationRequest = new OperationRequest(i, j);

			JAXBContext context = JAXBContext.newInstance(
					OperationRequest.class, OperationResponse.class);

			context.createMarshaller().marshal(operationRequest, doc);

			MessageFactory mf = MessageFactory
					.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
			SOAPMessage request = mf.createMessage();

			// Specify operation in a SOAP header
			addOperationSOAPHdr("subtract", request);

			SOAPBody body = request.getSOAPBody();

			body.addDocument(doc);

			SOAPMessage response = dispatch.invoke(request);

			OperationResponse operationResponse = context
					.createUnmarshaller()
					.unmarshal(response.getSOAPBody().getFirstChild(),
							OperationResponse.class).getValue();

			diff = operationResponse.getResult();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Difference of " + i + " and " + j + " is " + diff);
	}

	private void addOperationSOAPHdr(String operationName, SOAPMessage request)
			throws SOAPException {
		SOAPHeader opHdr = request.getSOAPHeader();
		QName opHdrQName = new QName(NAMESPACE_URI, "operation", "ns");
		SOAPHeaderElement opHdrElem = opHdr.addHeaderElement(opHdrQName);
		opHdrElem.setTextContent(operationName);
	}

	private void invokeException(int firstArg, int secondArg) {
		/*
		 * Operation multiply is not supported so the invocation throws an
		 * exception. The operation is specified in a SOAP attachment.
		 */

		try {
			invokeWithOperationInSOAPAtachment("multiply", firstArg, secondArg);
		} catch (SOAPFaultException e) {
			e.printStackTrace();
		}
	}

	private SOAPMessage invokeWithOperationInSOAPAtachment(
			String operationName, int firstArg, int secondArg) {
		SOAPMessage request = null;
		try {
			request = createRequestMessage();

			addOperationSOAPAttachment(operationName, request);

			SOAPBodyElement operation = getOperation(request);
			populateOperationArgs(operation, firstArg, secondArg);
		} catch (SOAPException e) {
			e.printStackTrace();
		}

		return dispatch.invoke(request);
	}

	private void addOperationSOAPAttachment(String operationName,
			SOAPMessage request) {
		AttachmentPart opAttachment = request.createAttachmentPart(
				operationName, "text/plain");
		// Content id becomes <operation> when it reaches the server
		opAttachment.setContentId("operation");

		request.addAttachmentPart(opAttachment);
	}

	// Invoke add using one style of SAAJ API
	private void invokeAdd1(int firstArg, int secondArg) {
		SOAPMessage response = invokeWithOperationInHTTPHdr("add", firstArg,
				secondArg);

		int sum = getResult(response);

		System.out.println("Sum of " + firstArg + " and " + secondArg + " is "
				+ sum);
	}

	// Invoke add using a different style of SAAJ API, specifically how the SOAP
	// body is obtained and added to the message
	private void invokeAdd2(int firstArg, int secondArg) {
		int sum = 0;

		/** Create SOAPMessage request. **/
		try {
			MessageFactory mf = MessageFactory
					.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
			// Create a message.
			SOAPMessage request = mf.createMessage();

			// Specify operation in a HTTP header
			addOperationHTTPHdr("add");

			// Obtain the SOAP body from SOAPEnvelope.
			SOAPEnvelope soapEnv = request.getSOAPPart().getEnvelope();

			// Construct the message payload.
			Name bodyName = soapEnv
					.createName("operation", "ns", NAMESPACE_URI);
			SOAPBody body = soapEnv.getBody();
			SOAPBodyElement operation = body.addBodyElement(bodyName);

			populateOperationArgs(operation, firstArg, secondArg);

			request.saveChanges();

			/** Invoke the service endpoint. **/
			SOAPMessage response = dispatch.invoke(request);

			sum = getResult(response);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Sum of " + firstArg + " and " + secondArg + " is "
				+ sum);
	}

	private SOAPMessage invokeWithOperationInHTTPHdr(String operationName,
			int firstArg, int secondArg) {
		addOperationHTTPHdr(operationName);

		SOAPMessage request = null;
		try {
			request = createRequestMessage();

			SOAPBodyElement operation = getOperation(request);
			populateOperationArgs(operation, firstArg, secondArg);
		} catch (SOAPException e) {
			e.printStackTrace();
		}

		return dispatch.invoke(request);
	}

	private void addOperationHTTPHdr(String opName) {
		Map<String, Object> reqCtx = dispatch.getRequestContext();

		Map<String, Object> reqHeaders = new HashMap<String, Object>();
		reqHeaders.put("operation", Collections.singletonList(opName));

		reqCtx.put(MessageContext.HTTP_REQUEST_HEADERS, reqHeaders);
	}

	private SOAPMessage createRequestMessage() throws SOAPException {
		MessageFactory mf = MessageFactory
				.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);

		return mf.createMessage();
	}

	private SOAPBodyElement getOperation(SOAPMessage request)
			throws SOAPException {
		SOAPBody body = request.getSOAPBody();

		QName bodyElemQName = new QName(NAMESPACE_URI, "operation", "ns");
		return body.addBodyElement(bodyElemQName);
	}

	private void populateOperationArgs(SOAPBodyElement operation, int firstArg,
			int secondArg) throws SOAPException {
		SOAPElement arg0 = operation.addChildElement("arg0");
		arg0.addTextNode(Integer.toString(firstArg));
		SOAPElement arg1 = operation.addChildElement("arg1");
		arg1.addTextNode(Integer.toString(secondArg));
	}

	private int getResult(SOAPMessage response) {
		SOAPBody body = null;
		try {
			body = response.getSOAPBody();
		} catch (SOAPException e) {
			e.printStackTrace();

			return 0;
		}

		@SuppressWarnings("unchecked")
		Iterator<SOAPElement> it = body.getChildElements(new QName(
				NAMESPACE_URI, "result"));

		return Integer.valueOf(it.next().getTextContent());
	}
}
