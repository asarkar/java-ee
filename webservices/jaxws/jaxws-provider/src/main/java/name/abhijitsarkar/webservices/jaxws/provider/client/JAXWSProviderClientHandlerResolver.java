package name.abhijitsarkar.webservices.jaxws.provider.client;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;

import name.abhijitsarkar.webservices.jaxws.handler.SOAPMessageLoggingHandler;

public class JAXWSProviderClientHandlerResolver implements HandlerResolver {

	@SuppressWarnings("rawtypes")
	@Override
	public List<Handler> getHandlerChain(PortInfo arg0) {
		List<Handler> handlerChain = new ArrayList<Handler>();
		handlerChain.add(new SOAPMessageLoggingHandler());

		return handlerChain;
	}
}
