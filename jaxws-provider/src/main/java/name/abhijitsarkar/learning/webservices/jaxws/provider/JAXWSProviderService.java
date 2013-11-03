package name.abhijitsarkar.learning.webservices.jaxws.provider;

import javax.jws.HandlerChain;
import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.ws.BindingType;
import javax.xml.ws.Provider;
import javax.xml.ws.Service;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.WebServiceProvider;
import javax.xml.ws.soap.SOAPBinding;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@WebServiceProvider(portName = "JAXWSProvider", serviceName = "JAXWSProviderService", targetNamespace = "http://abhijitsarkar.name/learning/webservices/jaxws/provider/")
@ServiceMode(Service.Mode.MESSAGE)
@BindingType(SOAPBinding.SOAP11HTTP_BINDING)
@HandlerChain(file = "jaxws-handler-chains.xml")
public class JAXWSProviderService implements Provider<Source> {

	@Override
	public Source invoke(Source data) {
		Node soapEnv = null;

		if (data instanceof DOMSource) {
			soapEnv = ((DOMSource) data).getNode().getFirstChild();
		} else {
			DOMResult dom = new DOMResult();
			try {
				Transformer tf = TransformerFactory.newInstance()
						.newTransformer();

				tf.transform(data, dom);
			} catch (Exception e) {
				throw new JAXWSProviderServiceFault(e.getMessage(), e);
			}

			soapEnv = dom.getNode().getFirstChild();
		}

		int result = parseInputAndComputeResult(soapEnv);

		return output(result);
	}

	private int parseInputAndComputeResult(Node soapEnv) {
		if (!("Envelope".equals(soapEnv.getLocalName()))) {
			String errorMsg = "Invalid request, expected Envelope, got "
					+ soapEnv.getLocalName();
			throw new JAXWSProviderServiceFault(errorMsg,
					new IllegalArgumentException(errorMsg));
		}

		NodeList childNodes = soapEnv.getChildNodes();

		if (childNodes == null || childNodes.getLength() == 0) {
			String errorMsg = "Invalid request, SOAP Body not found."
					+ soapEnv.getLocalName();
			throw new JAXWSProviderServiceFault(errorMsg,
					new IllegalArgumentException(errorMsg));
		}

		int numChildren = childNodes.getLength();

		Node soapBody = null;

		for (int i = 0; i < numChildren; i++) {
			if ("Body".equals(childNodes.item(i).getLocalName())) {
				soapBody = childNodes.item(i);
			}
		}

		if (soapBody == null) {
			String errorMsg = "Invalid request, SOAP Body not found."
					+ soapEnv.getLocalName();
			throw new JAXWSProviderServiceFault(errorMsg,
					new IllegalArgumentException(errorMsg));
		}

		Node operation = soapBody.getFirstChild();
		String operationName = operation.getLocalName();

		Node firstArgNode = operation.getFirstChild();

		if (firstArgNode == null) {
			String errorMsg = "Invalid request, expected two arguments, received none."
					+ soapEnv.getLocalName();
			throw new JAXWSProviderServiceFault(errorMsg,
					new IllegalArgumentException(errorMsg));
		}

		Node secondArgNode = firstArgNode.getNextSibling();

		if (secondArgNode == null) {
			String errorMsg = "Invalid request, expected two arguments, received one."
					+ soapEnv.getLocalName();
			throw new JAXWSProviderServiceFault(errorMsg,
					new IllegalArgumentException(errorMsg));
		}

		int firstArg = 0;
		int secondArg = 0;

		try {
			firstArg = Integer.valueOf(firstArgNode.getTextContent());
			secondArg = Integer.valueOf(secondArgNode.getTextContent());
		} catch (NumberFormatException e) {
			String errorMsg = "Invalid request, expected two numbers."
					+ soapEnv.getLocalName();
			throw new JAXWSProviderServiceFault(errorMsg,
					new IllegalArgumentException(errorMsg));
		}

		return computeResult(firstArg, secondArg, operationName);
	}

	private int computeResult(int firstArg, int secondArg, String operationName) {
		if ("add".equals(operationName)) {
			return firstArg + secondArg;
		} else if ("subtract".equals(operationName)) {
			return firstArg - secondArg;
		}

		String errorMsg = "Invalid request, only add and subtract operations are supported.";
		throw new JAXWSProviderServiceFault(errorMsg,
				new IllegalArgumentException(errorMsg));
	}

	private Source output(int result) {
		try {
			MessageFactory mf = MessageFactory
					.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);

			SOAPMessage msg = mf.createMessage();

			SOAPPart soapPart = ((SOAPMessage) msg).getSOAPPart();

			SOAPEnvelope soapEnv = soapPart.getEnvelope();

			SOAPBody soapBody = soapEnv.getBody();

			QName resultElementQName = new QName(
					"http://abhijitsarkar.name/learning/webservices/jaxws/provider/",
					"result", "ns");

			SOAPElement resultElement = soapBody
					.addChildElement(resultElementQName);
			resultElement.addTextNode(String.valueOf(result));

			msg.saveChanges();

			return soapPart.getContent();
		} catch (Exception e) {
			throw new JAXWSProviderServiceFault(e.getMessage(), e);
		}
	}
}
