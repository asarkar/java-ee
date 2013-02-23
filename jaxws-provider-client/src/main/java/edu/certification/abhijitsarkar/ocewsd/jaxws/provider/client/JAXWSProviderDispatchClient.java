package edu.certification.abhijitsarkar.ocewsd.jaxws.provider.client;

import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.xml.bind.JAXBContext;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import org.w3c.dom.Document;

import edu.certification.abhijitsarkar.ocewsd.jaxws.provider.client.bind.AddRequest;
import edu.certification.abhijitsarkar.ocewsd.jaxws.provider.client.bind.AddResponse;
import edu.certification.abhijitsarkar.ocewsd.jaxws.provider.client.handler.JAXWSProviderClientHandlerResolver;

public class JAXWSProviderDispatchClient {

	public JAXWSProviderDispatchClient() {
		/** Create a service and add at least one port to it. **/
		Service service = Service.create(serviceName);
		service.setHandlerResolver(new JAXWSProviderClientHandlerResolver());
		service.addPort(portName, SOAPBinding.SOAP12HTTP_BINDING, ENDPOINT_URL);

		/** Create a Dispatch instance from a service. **/
		dispatch = service.createDispatch(portName, SOAPMessage.class,
				Service.Mode.MESSAGE);
	}

	@SuppressWarnings("unchecked")
	public int invoke1(int i, int j) {
		int sum = Integer.MIN_VALUE;

		/** Create SOAPMessage request. **/
		// Compose a request message.
		try {
			MessageFactory mf = MessageFactory
					.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
			// Create a message.
			SOAPMessage request = mf.createMessage();

			// Obtain the SOAPEnvelope and header and body elements.
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
			Iterator<SOAPElement> it = body.getChildElements(new QName(
					NAMESPACE_URI, "addResponse"));
			it = it.next().getChildElements(new QName("sum"));

			sum = Integer.valueOf(it.next().getTextContent());
		} catch (SOAPException se) {
			se.printStackTrace();
		} catch (NoSuchElementException nsee) {
			nsee.printStackTrace();
		}

		return (sum > Integer.MIN_VALUE) ? sum : Integer.MIN_VALUE;
	}

	public int invoke2(int i, int j) {
		int sum = Integer.MIN_VALUE;

		try {
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory
					.newInstance();
			builderFactory.setNamespaceAware(true);
			Document doc = builderFactory.newDocumentBuilder().newDocument();

			AddRequest addRequest = new AddRequest(i, j);

			JAXBContext context = JAXBContext.newInstance(AddRequest.class,
					AddResponse.class);

			context.createMarshaller().marshal(addRequest, doc);

			MessageFactory mf = MessageFactory
					.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
			// Create a message.
			SOAPMessage request = mf.createMessage();

			// Obtain the body element.
			SOAPBody body = request.getSOAPBody();

			body.addDocument(doc);

			SOAPMessage response = dispatch.invoke(request);

			AddResponse addResponse = context
					.createUnmarshaller()
					.unmarshal(response.getSOAPBody().getFirstChild(),
							AddResponse.class).getValue();

			return addResponse.getResult();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return (sum > Integer.MIN_VALUE) ? sum : Integer.MIN_VALUE;
	}

	private static final String ENDPOINT_URL = "http://Abhijit-Lenovo:8080/JAXWSProviderService/edu.certification.abhijitsarkar.ocewsd.jaxws.provider.JAXWSProvider";
	private static final String NAMESPACE_URI = "http://abhijitsarkar/certification/edu/ocewsd/jaxws/provider/";
	private static final String SERVICE_NAME = "JAXWSProviderService";
	private static final String PORT_NAME = "JAXWSProvider";
	private static final QName serviceName = new QName(NAMESPACE_URI,
			SERVICE_NAME);
	private static final QName portName = new QName(NAMESPACE_URI, PORT_NAME);

	private Dispatch<SOAPMessage> dispatch = null;
}
