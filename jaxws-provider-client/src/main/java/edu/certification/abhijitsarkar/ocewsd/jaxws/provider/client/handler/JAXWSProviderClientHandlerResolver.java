package edu.certification.abhijitsarkar.ocewsd.jaxws.provider.client.handler;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;

import edu.certification.abhijitsarkar.ocewsd.jaxws.utility.handler.SOAPRequestHandler;

public class JAXWSProviderClientHandlerResolver implements HandlerResolver {

	@SuppressWarnings("rawtypes")
	@Override
	public List<Handler> getHandlerChain(PortInfo arg0) {
		List<Handler> handlerChain = new ArrayList<Handler>();
		handlerChain.add(new SOAPRequestHandler());

		return handlerChain;
	}
}
