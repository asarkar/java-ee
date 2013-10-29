package name.abhijitsarkar.learning.webservices.jaxws.handler;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import name.abhijitsarkar.util.logging.AppLogger;

import org.apache.log4j.Logger;
import org.w3c.dom.NodeList;

public class ProgrammaticAuthenticationHandler extends AbstractSOAPHandler {

	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		super.handleMessage(context);

		boolean inbound = !((Boolean) context
				.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY));

		logger.debug("Inbound: " + inbound);

		if (inbound) {
			String soapAction = getSOAPAction();

			if (soapAction == null || soapAction.trim().length() == 0) {
				logger.error("Bad request: SOAP action not found.");
				throw new WebServiceException(
						"Bad request: SOAP action not found.");
			}

			if (!PROGRAMMATIC_AUTHENTICATION.equals(soapAction)) {
				return true;
			}

			SOAPHeader header = null;

			try {
				header = context.getMessage().getSOAPHeader();
			} catch (SOAPException e) {
				logger.error("Unable to extract SOAP header.");
				throw new WebServiceException(
						"Internal server error: unable to extract SOAP header.");
			}

			if (header == null) {
				logger.error("SOAP header not found.");
				throw new WebServiceException(
						"Bad request: SOAP header not found.");
			}

			NodeList childElements = header
					.getElementsByTagName(SECRET_ACCESS_KEY_PROPERTY);

			if (childElements == null || childElements.getLength() == 0) {
				logger.error("Bad request: secret access key not found.");
				throw new WebServiceException(
						"Bad request: secret access key not found.");
			}

			String secretAccessKey = childElements.item(0).getTextContent();

			logger.debug("Secret Access Key: " + secretAccessKey);

			if (!SECRET_ACCESS_KEY.equals(secretAccessKey)) {
				logger.error("Authentication error: secret access key '"
						+ secretAccessKey + "' did not match.");
				throw new WebServiceException(
						"Authentication error: secret access key '"
								+ secretAccessKey + "' did not match.");
			}
		}

		return true;
	}

	private static final String SECRET_ACCESS_KEY_PROPERTY = "secret-access-key";
	private static final String SECRET_ACCESS_KEY = "asarkar";
	private static final String PROGRAMMATIC_AUTHENTICATION = "pgm-auth";
	private static final Logger logger = AppLogger
			.getInstance(ProgrammaticAuthenticationHandler.class);
}
