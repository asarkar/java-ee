package com.github.mkalin.jwsur2.ch6.wssecurity.client;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import com.sun.xml.wss.ProcessingContext;
import com.sun.xml.wss.XWSSProcessor;
import com.sun.xml.wss.XWSSProcessorFactory;

public class ClientHandler implements SOAPHandler<SOAPMessageContext> {
	private XWSSProcessor xwssClient;
	private boolean trace;

	public ClientHandler() {
		XWSSProcessorFactory fact = null;
		try {
			fact = XWSSProcessorFactory.newInstance();
			InputStream config = getClass().getResourceAsStream(
					"/wssecurity-client.xml");
			xwssClient = fact.createProcessorForSecurityConfiguration(config,
					new Prompter());
			config.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		trace = true; // set to true to enable message dumps
	}

	// Add a security header block
	public Set<QName> getHeaders() {
		String uri = "http://docs.oasis-open.org/wss/2004/01/"
				+ "oasis-200401-wss-wssecurity-secext-1.0.xsd";
		QName securityHdr = new QName(uri, "Security", "wsse");
		HashSet<QName> headers = new HashSet<QName>();
		headers.add(securityHdr);
		return headers;
	}

	public boolean handleMessage(SOAPMessageContext msgCtx) {
		Boolean outbound = (Boolean) msgCtx
				.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		SOAPMessage msg = msgCtx.getMessage();

		if (outbound.booleanValue()) {
			ProcessingContext pCtx = null;
			try {
				pCtx = xwssClient.createProcessingContext(msg);
				pCtx.setSOAPMessage(msg);
				SOAPMessage secureMsg = xwssClient.secureOutboundMessage(pCtx);
				msgCtx.setMessage(secureMsg);

				if (trace)
					dump("Outgoing message:", secureMsg);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return true;
	}

	public boolean handleFault(SOAPMessageContext msgCtx) {
		return true;
	}

	public void close(MessageContext msgCtx) {
	}

	private void dump(String msg, SOAPMessage soapMsg) {
		try {
			System.out.println(msg);
			soapMsg.writeTo(System.out);
			System.out.println();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
