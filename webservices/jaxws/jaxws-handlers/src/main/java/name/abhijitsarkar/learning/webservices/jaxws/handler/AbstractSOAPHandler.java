package name.abhijitsarkar.learning.webservices.jaxws.handler;

import java.util.Collections;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public abstract class AbstractSOAPHandler implements
		SOAPHandler<SOAPMessageContext> {

	public final HandlerUtil handlerUtil;

	public AbstractSOAPHandler() {
		handlerUtil = new HandlerUtil();
	}

	@Override
	public void close(MessageContext context) {
	}

	@Override
	public boolean handleMessage(SOAPMessageContext messageContext) {
		return true;
	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		return true;
	}

	@Override
	public Set<QName> getHeaders() {
		return Collections.emptySet();
	}
}
