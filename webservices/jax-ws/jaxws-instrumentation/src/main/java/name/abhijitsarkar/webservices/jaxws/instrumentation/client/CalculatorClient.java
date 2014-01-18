package name.abhijitsarkar.webservices.jaxws.instrumentation.client;

import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;

public class CalculatorClient {
	private final Dispatch<SOAPMessage> dispatch;
	private String namespaceUri;

	public CalculatorClient(Service service, QName portQName,
			String namespaceUri) {
		/** Create a Dispatch instance from a service. **/
		dispatch = service.createDispatch(portQName, SOAPMessage.class,
				Service.Mode.MESSAGE);
		this.namespaceUri = namespaceUri;
	}

	// TODO: Use JAXB
	int invokeAdd(int firstArg, int secondArg) {
		int sum = 0;

		/** Create SOAPMessage request. **/
		try {
			MessageFactory mf = MessageFactory
					.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
			// Create a message.
			SOAPMessage request = mf.createMessage();

			// Obtain the SOAP body from SOAPEnvelope.
			SOAPEnvelope soapEnv = request.getSOAPPart().getEnvelope();

			// Construct the message payload.
			Name bodyName = soapEnv.createName("add", "ns", namespaceUri);
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

		return sum;
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
				namespaceUri, "addResponse"));

		return Integer.valueOf(it.next().getTextContent());
	}
}
