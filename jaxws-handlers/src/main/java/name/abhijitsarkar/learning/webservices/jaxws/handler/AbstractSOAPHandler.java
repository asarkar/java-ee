package name.abhijitsarkar.learning.webservices.jaxws.handler;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import name.abhijitsarkar.util.logging.AppLogger;

import org.apache.log4j.Logger;

public abstract class AbstractSOAPHandler implements
		SOAPHandler<SOAPMessageContext> {

	@Override
	public void close(MessageContext context) {
		// no-op
	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		return true;
	}

	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		boolean inbound = !((Boolean) context
				.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY));

		logger.debug("Inbound: " + inbound);

		if (inbound) {
			List<String> soapAction = null;

			@SuppressWarnings("unchecked")
			Map<String, List<String>> requestHdrs = (Map<String, List<String>>) context
					.get(MessageContext.HTTP_REQUEST_HEADERS);

			if (requestHdrs != null) {
				soapAction = requestHdrs.get("SOAPAction");

				if (soapAction != null && soapAction.size() != 0) {
					logger.debug("SOAP Action: " + soapAction.get(0));
					this.soapAction = soapAction.get(0).trim()
							.replace("\"", "");
				}
			}
		}

		return true;
	}

	@Override
	public Set<QName> getHeaders() {
		return null;
	}

	protected String getSOAPAction() {
		return this.soapAction;
	}

	private static final Logger logger = AppLogger
			.getInstance(AbstractSOAPHandler.class);

	private String soapAction;
}
