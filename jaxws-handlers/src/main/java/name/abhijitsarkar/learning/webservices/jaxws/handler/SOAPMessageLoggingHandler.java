package name.abhijitsarkar.learning.webservices.jaxws.handler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class SOAPMessageLoggingHandler implements
		SOAPHandler<SOAPMessageContext> {

	private static final Transformer transformer = newTransformer();

	public boolean handleMessage(SOAPMessageContext messageContext) {
		boolean outbound = (Boolean) messageContext
				.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		prettyPrint(messageContext.getMessage(), outbound);

		return true;
	}

	public Set<QName> getHeaders() {
		return Collections.emptySet();
	}

	public boolean handleFault(SOAPMessageContext messageContext) {
		return true;
	}

	public void close(MessageContext context) {
	}

	private void prettyPrint(SOAPMessage soapMessage, boolean outbound) {
		ByteArrayOutputStream streamOut = null;
		try {
			Source soapEnvelope = new DOMSource(soapMessage.getSOAPPart()
					.getEnvelope());

			streamOut = new ByteArrayOutputStream();
			StreamResult result = new StreamResult(streamOut);

			if (outbound) {
				System.out.println("Outbound (request) SOAP message");
			} else {
				System.out.println("Inbound (response) SOAP message");
			}

			transform(soapEnvelope, result);

			System.out.println("SOAPEnvelope: "
					+ System.getProperty("line.separator")
					+ streamOut.toString());

			System.out.println("Number of attachments: "
					+ soapMessage.countAttachments());

		} catch (SOAPException e) {
			e.printStackTrace();
		} finally {
			if (streamOut != null) {
				try {
					streamOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void transform(Source soap, StreamResult result) {
		if (transformer == null) {
			return;
		}

		initTransformer();

		try {
			transformer.transform(soap, result);
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

	private static Transformer newTransformer() {
		TransformerFactory factory = TransformerFactory.newInstance();
		try {
			return factory.newTransformer();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void initTransformer() {
		transformer.reset();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(
				"{http://xml.apache.org/xslt}indent-amount", "2");
	}
}
