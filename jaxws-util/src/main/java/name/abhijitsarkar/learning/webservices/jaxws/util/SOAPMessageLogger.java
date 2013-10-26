package name.abhijitsarkar.learning.webservices.jaxws.util;

import java.io.ByteArrayOutputStream;
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

public class SOAPMessageLogger implements SOAPHandler<SOAPMessageContext> {

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
		try {
			Source soapEnvelope = new DOMSource(soapMessage.getSOAPPart()
					.getEnvelope());

			Transformer tf = getTransformer();

			if (tf == null) {
				return;
			}

			ByteArrayOutputStream streamOut = new ByteArrayOutputStream();
			StreamResult result = new StreamResult(streamOut);

			if (outbound) {
				System.out.println("Outbound (request) SOAP message");
			} else {
				System.out.println("Inbound (response) SOAP message");
			}

			transform(tf, soapEnvelope, result);

			System.out.println("SOAPEnvelope: "
					+ System.getProperty("line.separator")
					+ streamOut.toString());

			System.out.println("Number of attachments: "
					+ soapMessage.countAttachments());

		} catch (SOAPException e) {
			e.printStackTrace();
		}
	}

	private void transform(Transformer tf, Source soap, StreamResult result) {
		initTransformer(tf);

		try {
			tf.transform(soap, result);
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

	private Transformer getTransformer() {
		TransformerFactory factory = TransformerFactory.newInstance();
		try {
			return factory.newTransformer();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void initTransformer(Transformer tf) {
		tf.reset();
		tf.setOutputProperty(OutputKeys.INDENT, "yes");
		tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
	}
}
