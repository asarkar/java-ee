package edu.certification.abhijitsarkar.ocewsd.jaxws.ejb.endpoint.handler;

import java.util.List;
import java.util.Map;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.apache.log4j.Logger;

import edu.certification.abhijitsarkar.ocewsd.jaxws.utility.handler.AbstractSOAPHandler;
import edu.certification.abhijitsarkar.ocewsd.utility.logging.AppLogger;

public class HttpBasicAuthenticationHandler extends AbstractSOAPHandler {

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

			if (!HTTP_BASIC_AUTHENTICATION.equals(soapAction)) {
				return true;
			}

			@SuppressWarnings("unchecked")
			Map<String, List<String>> requestHdrs = (Map<String, List<String>>) context
					.get(MessageContext.HTTP_REQUEST_HEADERS);

			if (requestHdrs == null || requestHdrs.size() == 0) {
				logger.error("Bad request: Http request headers not found.");
				throw new WebServiceException(
						"Bad request: Http request headers not found.");
			}

			List<String> username = requestHdrs
					.get(BindingProvider.USERNAME_PROPERTY);
			List<String> password = requestHdrs
					.get(BindingProvider.PASSWORD_PROPERTY);

			if (username == null || username.size() == 0 || password == null
					|| password.size() == 0) {
				logger.error("Bad request: credentials not found.");
				throw new WebServiceException(
						"Bad request: credentials not found.");
			}

			logger.debug("Username: " + username.get(0));
			logger.debug("Password: " + password.get(0));

			if (!THE_ONLY_VALID_USERNAME.equals(username.get(0))
					|| !THE_ONLY_VALID_PASSWORD.equals(password.get(0))) {
				logger.error("Authentication error:  credentials did not match.");
				throw new WebServiceException(
						"Authentication error:  credentials did not match.");
			}
		}

		return true;
	}

	private static final String THE_ONLY_VALID_USERNAME = "asarkar";
	private static final String THE_ONLY_VALID_PASSWORD = "abhijitsarkar";
	private static final String HTTP_BASIC_AUTHENTICATION = "http-basic-auth";
	private static final Logger logger = AppLogger
			.getInstance(HttpBasicAuthenticationHandler.class);
}
