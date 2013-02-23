package com.amazon.webservices.awsecommerceservice._2011_08_01.client.handler;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.LogicalMessage;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.handler.LogicalHandler;
import javax.xml.ws.handler.LogicalMessageContext;
import javax.xml.ws.handler.MessageContext;

import org.apache.log4j.Logger;

import edu.certification.abhijitsarkar.ocewsd.utility.logging.AppLogger;

public class RequestSignatureHandler implements
		LogicalHandler<LogicalMessageContext> {

	private static final Logger logger = AppLogger
			.getInstance(RequestSignatureHandler.class);

	@Override
	public void close(MessageContext messageContext) {
		// no-op

	}

	@Override
	public boolean handleFault(LogicalMessageContext messageContext) {
		return true;
	}

	@Override
	public boolean handleMessage(LogicalMessageContext messageContext) {
		boolean outbound = (Boolean) messageContext
				.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		LogicalMessage message = messageContext.getMessage();

		if (outbound) {
			Source payload = message.getPayload();
			Result result = new RequestTransformer().transform(payload);
			if (!(result instanceof StreamResult)) {
				logger.error("Expected javax.xml.transform.stream.StreamSource but got "
						+ result.getClass().getName());
				throw new WebServiceException(
						"Expected javax.xml.transform.stream.StreamSource but got "
								+ result.getClass().getName());
			}
			String xml = ((StreamResult) result).getWriter().toString();
			try {
				message.setPayload(new StreamSource(new ByteArrayInputStream(
						xml.getBytes("UTF-8"))));
			} catch (UnsupportedEncodingException e) {
				logger.error(
						"How the heck did this happen? UTF-8 is available in all JVMs",
						e);
				throw new WebServiceException(
						"How the heck did this happen? UTF-8 is available in all JVMs",
						e);
			}
		}
		return true;
	}
}
