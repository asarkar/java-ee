package name.abhijitsarkar.webservices.jaxws.handler;

import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import name.abhijitsarkar.util.logging.AppLogger;

import org.apache.log4j.Logger;

public class SOAPMessageLoggingHandler extends AbstractSOAPHandler {
	private static final Logger logger = AppLogger
			.getInstance(SOAPMessageLoggingHandler.class);

	@Override
	public boolean handleMessage(SOAPMessageContext messageContext) {
		boolean outbound = (Boolean) messageContext
				.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

		if (outbound) {
			logger.debug("Outbound SOAP message");
		} else {
			logger.debug("Inbound SOAP message");
		}

		handlerUtil.prettyPrintSOAPMessage(messageContext.getMessage());

		return true;
	}
	
	@Override
	public boolean handleFault(SOAPMessageContext messageContext) {
		handlerUtil.prettyPrintSOAPMessage(messageContext.getMessage());

		return true;
	}
}
