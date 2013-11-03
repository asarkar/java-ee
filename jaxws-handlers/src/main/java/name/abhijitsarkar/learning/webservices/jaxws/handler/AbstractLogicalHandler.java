package name.abhijitsarkar.learning.webservices.jaxws.handler;

import javax.xml.ws.handler.LogicalHandler;
import javax.xml.ws.handler.LogicalMessageContext;
import javax.xml.ws.handler.MessageContext;

public abstract class AbstractLogicalHandler implements
		LogicalHandler<LogicalMessageContext> {

	public final HandlerUtil handlerUtil;

	public AbstractLogicalHandler() {
		handlerUtil = new HandlerUtil();
	}

	@Override
	public void close(MessageContext context) {

	}

	@Override
	public boolean handleFault(LogicalMessageContext context) {
		return true;
	}

	@Override
	public boolean handleMessage(LogicalMessageContext msgCtx) {
		return true;
	}
}
