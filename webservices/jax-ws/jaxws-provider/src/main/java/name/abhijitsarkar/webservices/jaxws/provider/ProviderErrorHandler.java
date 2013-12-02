package name.abhijitsarkar.webservices.jaxws.provider;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.ws.LogicalMessage;
import javax.xml.ws.handler.LogicalMessageContext;
import javax.xml.ws.handler.MessageContext;

import name.abhijitsarkar.util.logging.AppLogger;
import name.abhijitsarkar.webservices.jaxws.handler.AbstractLogicalHandler;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;

public class ProviderErrorHandler extends AbstractLogicalHandler {
	private static final Logger logger = AppLogger
			.getInstance(ProviderErrorHandler.class);

	@Override
	public boolean handleFault(LogicalMessageContext msgCtx) {
		boolean outbound = Boolean.TRUE.equals(msgCtx
				.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY));

		if (outbound) {
			LogicalMessage msg = msgCtx.getMessage();

			DOMResult dom = new DOMResult();
			try {
				Source payload = msg.getPayload();

				Node node = null;
				// If payload is not a DomSource, need to transform
				if (!(payload instanceof DOMSource)) {
					Transformer tf = TransformerFactory.newInstance()
							.newTransformer();
					tf.transform(payload, dom);

					node = dom.getNode();
				} else {
					node = ((DOMSource) payload).getNode();
				}

				Node fault = node.getFirstChild();

				Node faultCode = fault.getFirstChild();

				Node faultString = faultCode.getNextSibling();

				Node detail = faultString.getNextSibling();

				// Remove the detail stacktrace, client doesn't need to see it
				if (detail != null) {
					fault.removeChild(detail);
				}

				// If payload is not a DomSource, need to set back
				if (!(payload instanceof DOMSource)) {
					logger.debug("Alas, payload is not a DomSource, it is "
							+ payload.getClass().getName());
					payload = new DOMSource(fault);

					msg.setPayload(payload);
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);

				return false;
			}
		}

		return true;
	}
}
