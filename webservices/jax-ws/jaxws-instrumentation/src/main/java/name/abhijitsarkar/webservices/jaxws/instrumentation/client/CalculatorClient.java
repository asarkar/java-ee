package name.abhijitsarkar.webservices.jaxws.instrumentation.client;

import javax.xml.bind.JAXBContext;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;

import org.w3c.dom.Document;

public class CalculatorClient {
	private final Dispatch<SOAPMessage> dispatch;

	public CalculatorClient(Service service, QName portQName) {
		/** Create a Dispatch instance from a service. **/
		dispatch = service.createDispatch(portQName, SOAPMessage.class,
				Service.Mode.MESSAGE);
	}

	// Note: When Metro client monitoring is enabled, it is the application's
	// responsibility to close the port proxy by casting it to Closeable and
	// invoking close() on it. There's no way to get a port without a SEI so
	// dispatch clients could cause resource leak if Metro client monitoring is
	// enabled.
	int invokeAdd(AddRequest addRequest) {
		int sum = 0;

		try {
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory
					.newInstance();
			builderFactory.setNamespaceAware(true);
			Document doc = builderFactory.newDocumentBuilder().newDocument();

			JAXBContext context = JAXBContext.newInstance(AddRequest.class,
					AddResponse.class);

			context.createMarshaller().marshal(addRequest, doc);

			MessageFactory mf = MessageFactory
					.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
			SOAPMessage request = mf.createMessage();

			SOAPBody body = request.getSOAPBody();

			body.addDocument(doc);

			SOAPMessage response = dispatch.invoke(request);

			AddResponse addResponse = context
					.createUnmarshaller()
					.unmarshal(response.getSOAPBody().getFirstChild(),
							AddResponse.class).getValue();

			sum = addResponse.getSum();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sum;
	}
}
