package edu.certification.abhijitsarkar.ocewsd.jaxws.utility.handler;

import java.io.ByteArrayOutputStream;

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
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.apache.log4j.Logger;

import edu.certification.abhijitsarkar.ocewsd.utility.logging.AppLogger;

public class SOAPRequestHandler extends AbstractSOAPHandler {
	private static final Logger logger = AppLogger
			.getInstance(SOAPRequestHandler.class);

	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		boolean outbound = (Boolean) context
				.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		prettyPrint(context.getMessage(), outbound);

		return true;
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
				logger.debug("Outbound (request) SOAP message");
			} else {
				logger.debug("Inbound (response) SOAP message");
			}

			transform(tf, soapEnvelope, result);

			logger.debug("SOAPEnvelope: "
					+ System.getProperty("line.separator")
					+ streamOut.toString());

			logger.debug("Number of attachments: "
					+ soapMessage.countAttachments());

		} catch (SOAPException e) {
			logger.error("Error trying to pretty print the SOAPEnvelope", e);
		}
	}

	private void transform(Transformer tf, Source soap, StreamResult result) {
		initTransformer(tf);

		try {
			tf.transform(soap, result);
		} catch (TransformerException e) {
			logger.error("Error trying to transform the SOAP element", e);
		}
	}

	private Transformer getTransformer() {
		TransformerFactory factory = TransformerFactory.newInstance();
		try {
			return factory.newTransformer();
		} catch (TransformerConfigurationException e) {
			logger.error("Error trying to create a new transformer", e);
		}
		return null;
	}

	private void initTransformer(Transformer tf) {
		tf.reset();
		tf.setOutputProperty(OutputKeys.INDENT, "yes");
		tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
	}
}
