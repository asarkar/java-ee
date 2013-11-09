package com.github.mkalin.jwsur2.ch6.wssecurity;

import java.io.ByteArrayInputStream;
import java.util.HashSet;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import com.sun.xml.wss.ProcessingContext;
import com.sun.xml.wss.SubjectAccessor;
import com.sun.xml.wss.XWSSProcessor;
import com.sun.xml.wss.XWSSProcessorFactory;

public class ServiceHandler implements SOAPHandler<SOAPMessageContext> {
	private XWSSProcessor xwssServer = null;
	private boolean trace;

	public ServiceHandler() {
		XWSSProcessorFactory fact = null;
		try {
			fact = XWSSProcessorFactory.newInstance();
			ByteArrayInputStream config = getConfig();
			xwssServer = fact.createProcessorForSecurityConfiguration(config,
					new Verifier());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		trace = true; // set to true to enable message dumps
	}

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

		if (!outbound.booleanValue()) {
			// Validate the message.
			try {
				ProcessingContext pCtx = xwssServer
						.createProcessingContext(msg);
				pCtx.setSOAPMessage(msg);
				SOAPMessage verifiedMsg = xwssServer.verifyInboundMessage(pCtx);

				System.out.println(SubjectAccessor.getRequesterSubject(pCtx));

				msgCtx.setMessage(verifiedMsg);
				if (trace)
					dump("Incoming message:", verifiedMsg);
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

	private ByteArrayInputStream getConfig() {
		String config = "<xwss:SecurityConfiguration xmlns:xwss=\"http://java.sun.com/xml/ns/xwss/config\" dumpMessages=\"true\"><xwss:RequireUsernameToken passwordDigestRequired=\"false\"/></xwss:SecurityConfiguration>";
		return new ByteArrayInputStream(config.getBytes());
	}
}
