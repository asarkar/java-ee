package name.abhijitsarkar.webservices.jaxws.provider;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.HandlerChain;
import javax.xml.namespace.QName;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.ws.BindingType;
import javax.xml.ws.Provider;
import javax.xml.ws.Service;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.WebServiceProvider;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.soap.SOAPBinding;

import org.w3c.dom.Node;

@WebServiceProvider(portName = "JAXWSProvider", serviceName = "JAXWSProviderService", targetNamespace = "http://abhijitsarkar.name/webservices/jaxws/provider/")
// The SOAPMessage, minus attachments. Default mode is PAYLOAD in which the
// Provider only has access to the message payload
@ServiceMode(Service.Mode.MESSAGE)
@BindingType(SOAPBinding.SOAP11HTTP_BINDING)
@HandlerChain(file = "jaxws-handler-chains.xml")
public class JAXWSProviderService implements Provider<SOAPMessage> {
	@Resource
	WebServiceContext ctx;

	@Override
	public SOAPMessage invoke(SOAPMessage soapMsg) {
		int[] args = null;
		Operation operation = null;

		try {
			SOAPEnvelope soapEnv = soapMsg.getSOAPPart().getEnvelope();
			SOAPBody soapBody = soapEnv.getBody();

			args = getArgs(soapBody);

			operation = getOperation(soapMsg);
		} catch (SOAPException e) {
			String errorMsg = "A problem occurred while parsing the SOAPMessage";
			throw new JAXWSProviderServiceFault(errorMsg,
					new IllegalArgumentException(errorMsg));
		}

		int result = computeResult(operation, args[0], args[1]);

		return output(result);
	}

	private int[] getArgs(SOAPBody soapBody) {
		Node operation = soapBody.getFirstChild();

		Node firstArgNode = operation.getFirstChild();

		if (firstArgNode == null) {
			String errorMsg = "Invalid request, expected two arguments, received none.";
			throw new JAXWSProviderServiceFault(errorMsg,
					new IllegalArgumentException(errorMsg));
		}

		Node secondArgNode = firstArgNode.getNextSibling();

		if (secondArgNode == null) {
			String errorMsg = "Invalid request, expected two arguments, received one.";
			throw new JAXWSProviderServiceFault(errorMsg,
					new IllegalArgumentException(errorMsg));
		}

		int firstArg = 0;
		int secondArg = 0;

		try {
			firstArg = Integer.valueOf(firstArgNode.getTextContent());
			secondArg = Integer.valueOf(secondArgNode.getTextContent());
		} catch (NumberFormatException e) {
			String errorMsg = "Invalid request, expected two numbers.";
			throw new JAXWSProviderServiceFault(errorMsg,
					new IllegalArgumentException(errorMsg));
		}

		return new int[] { firstArg, secondArg };
	}

	/*
	 * The method tries to find the operation in the following order: 1. It
	 * looks in the HTTP headers. 2. It then looks in the SOAP headers. 3.
	 * Finally, it looks in the SOAP attachments.
	 * 
	 * If it couldn't find a requested operation, it throws an exception.
	 */
	@SuppressWarnings("unchecked")
	private Operation getOperation(SOAPMessage soapMsg) throws SOAPException {
		String op = getOperationFromHTTPHdr();

		if (op == null) {
			System.out
					.println("Operation not found in HTTP headers...trying SOAP headers");

			op = getOperationFromSOAPHdr(soapMsg.getSOAPHeader());

			if (op == null) {
				System.out
						.println("Operation not found in SOAP headers...trying SOAP attachments");

				op = getOperationFromSOAPAttachment(soapMsg.getAttachments());

				if (op == null) {
					String errorMsg = "Invalid request, operation not found.";
					throw new JAXWSProviderServiceFault(errorMsg,
							new IllegalArgumentException(errorMsg));
				}
			}
		}

		return Operation.findByValue(op);
	}

	private String getOperationFromHTTPHdr() {
		MessageContext msgCtx = ctx.getMessageContext();

		@SuppressWarnings("unchecked")
		Map<String, List<String>> requestHdrs = (Map<String, List<String>>) msgCtx
				.get(MessageContext.HTTP_REQUEST_HEADERS);

		List<String> opHdr = requestHdrs.get("operation");

		if (opHdr != null && opHdr.size() > 0) {
			System.out.println("Found operation in HTTP header");

			return opHdr.get(0);
		}

		return null;
	}

	private String getOperationFromSOAPHdr(SOAPHeader soapHeader) {
		@SuppressWarnings("unchecked")
		Iterator<SOAPHeaderElement> it = soapHeader.examineAllHeaderElements();

		SOAPHeaderElement opHdr = null;

		while (it.hasNext()) {
			opHdr = it.next();

			if ("operation".equals(opHdr.getLocalName())) {
				System.out.println("Found operation in SOAP header");

				return opHdr.getTextContent();
			}
		}

		return null;
	}

	private String getOperationFromSOAPAttachment(Iterator<AttachmentPart> it)
			throws SOAPException {
		AttachmentPart opAttachment = null;

		while (it.hasNext()) {
			opAttachment = it.next();

			if ("<operation>".equals(opAttachment.getContentId())) {
				System.out.println("Found operation in SOAP attachment");

				return opAttachment.getContent().toString();
			}
		}

		return null;
	}

	private int computeResult(Operation operation, int firstArg, int secondArg) {
		switch (operation) {
		case ADD:
			return firstArg + secondArg;
		case SUBTRACT:
			return firstArg - secondArg;
		default:
			String errorMsg = "Invalid request, only add and subtract operations are supported.";
			throw new JAXWSProviderServiceFault(errorMsg,
					new IllegalArgumentException(errorMsg));
		}
	}

	private SOAPMessage output(int result) {
		try {
			MessageFactory mf = MessageFactory
					.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);

			SOAPMessage msg = mf.createMessage();

			SOAPPart soapPart = ((SOAPMessage) msg).getSOAPPart();

			SOAPEnvelope soapEnv = soapPart.getEnvelope();

			SOAPBody soapBody = soapEnv.getBody();

			QName resultElementQName = new QName(
					"http://abhijitsarkar.name/webservices/jaxws/provider/",
					"result", "ns");

			SOAPElement resultElement = soapBody
					.addChildElement(resultElementQName);
			resultElement.addTextNode(String.valueOf(result));

			msg.saveChanges();

			return msg;
		} catch (Exception e) {
			throw new JAXWSProviderServiceFault(e.getMessage(), e);
		}
	}

	private static enum Operation {
		ADD("add"), SUBTRACT("subtract");

		private final String value;
		private final static Operation[] operations = Operation.values();

		private Operation(String value) {
			this.value = value;
		}

		public static Operation findByValue(String value) {
			for (Operation anOp : operations) {
				if (anOp.value.equals(value)) {
					return anOp;
				}
			}
			throw new IllegalArgumentException("Unsupported operation: "
					+ value);
		}
	}
}
