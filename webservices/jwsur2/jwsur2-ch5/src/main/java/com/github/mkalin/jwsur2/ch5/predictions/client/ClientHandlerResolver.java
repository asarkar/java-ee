package com.github.mkalin.jwsur2.ch5.predictions.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.LogicalMessage;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.LogicalHandler;
import javax.xml.ws.handler.LogicalMessageContext;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.PortInfo;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import name.abhijitsarkar.webservices.jaxws.handler.SOAPMessageLoggingHandler;

import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.github.mkalin.jwsur2.ch5.predictions.client.generated.Delete;
import com.github.mkalin.jwsur2.ch5.predictions.client.generated.Edit;
import com.github.mkalin.jwsur2.ch5.predictions.client.generated.GetOne;
import com.github.mkalin.jwsur2.ch5.predictions.client.generated.ObjectFactory;

public class ClientHandlerResolver implements HandlerResolver {
	private String name;
	private String key;

	public ClientHandlerResolver(String name, String key) {
		this.name = name;
		this.key = key;
	}

	@SuppressWarnings("rawtypes")
	public List<Handler> getHandlerChain(PortInfo portInfo) {
		List<Handler> handlerChain = new ArrayList<Handler>();
		handlerChain.add(new ClientHashHandler(this.name, this.key));
		handlerChain.add(new SOAPMessageLoggingHandler());
		// It is intentionally added last but will be invoked first in the chain
		// for requests and last in the chain for responses
		handlerChain.add(new IdHandler());
		return handlerChain;
	}
}

class ClientHashHandler implements SOAPHandler<SOAPMessageContext> {
	private byte[] secretBytes;
	private String name;

	public ClientHashHandler(String name, String key) {
		this.name = name;
		this.secretBytes = getBytes(key);
	}

	public void close(MessageContext mCtx) {
	}

	public Set<QName> getHeaders() {
		return null;
	}

	public boolean handleFault(SOAPMessageContext mCtx) {
		// For now, just print the message to the stderr.
		try {
			SOAPMessage msg = mCtx.getMessage();
			msg.writeTo(System.err);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return true;
	}

	public boolean handleMessage(SOAPMessageContext mCtx) {
		Boolean outbound = (Boolean) mCtx
				.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		if (outbound) {
			try {
				SOAPMessage soapMessage = mCtx.getMessage();
				SOAPEnvelope envelope = soapMessage.getSOAPPart().getEnvelope();

				soapMessage.writeTo(System.out);

				// Ensure there is a header and add a 'wrapper' element.
				if (envelope.getHeader() == null)
					envelope.addHeader();
				SOAPHeader header = envelope.getHeader();
				QName qn = new QName(
						"http://predictions.ch5.jwsur2.mkalin.github.com/",
						"credentials");
				header.addHeaderElement(qn);

				// Now insert credentials into the header.
				String timeStamp = getTimestamp();
				String signature = getSignature(this.name, timeStamp,
						this.secretBytes);

				Node firstChild = header.getFirstChild();
				append(firstChild, "Name", this.name);
				append(firstChild, "Signature", signature);
				append(firstChild, "Timestamp", timeStamp);

				soapMessage.saveChanges();
				// soapMessage.writeTo(System.out);
			} catch (Exception e) {
				throw new RuntimeException("SOAPException thrown.", e);
			}
		}
		return true; // continue down the handler chain
	}

	private String getSignature(String name, String timestamp,
			byte[] secretBytes) {
		try {
			// System.out.println("Name ==      " + name);
			// System.out.println("Timestamp == " + timestamp);

			String toSign = name + timestamp;
			byte[] toSignBytes = getBytes(toSign);

			Mac signer = Mac.getInstance("HmacSHA256");
			SecretKeySpec keySpec = new SecretKeySpec(secretBytes, "HmacSHA256");

			signer.init(keySpec);
			signer.update(toSignBytes);
			byte[] signBytes = signer.doFinal();

			String signature = new String(Base64.encodeBase64(signBytes));
			return signature;
		} catch (Exception e) {
			throw new RuntimeException("NoSuchAlgorithmException thrown.", e);
		}
	}

	private String getTimestamp() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss'Z'");
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		return dateFormat.format(calendar.getTime());
	}

	private void append(Node node, String elementName, String elementText) {
		Element element = node.getOwnerDocument().createElement(elementName);
		element.setTextContent(elementText);
		node.appendChild(element);
	}

	private byte[] getBytes(String str) {
		try {
			return str.getBytes("UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

class IdHandler implements LogicalHandler<LogicalMessageContext> {
	public void close(MessageContext mctx) {
	}

	public boolean handleFault(LogicalMessageContext lmctx) {
		return true;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean handleMessage(LogicalMessageContext lmctx) {
		Boolean outbound = (Boolean) lmctx
				.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		if (outbound) { // request?
			LogicalMessage msg = lmctx.getMessage();
			try {
				JAXBContext jaxbCtx = JAXBContext
						.newInstance(ObjectFactory.class);
				Object payload = msg.getPayload(jaxbCtx);
				// Check payload to be sure it's what we want.
				if (payload instanceof JAXBElement) {
					Object value = ((JAXBElement) payload).getValue();
					// Three possibilities of interest: GetOne, Edit, or
					// Delete
					int id = 0;
					boolean getOne, edit, delete;
					getOne = edit = delete = false;
					if (value.toString().contains("GetOne")) {
						id = ((GetOne) value).getArg0();
						getOne = true;
					} else if (value.toString().contains("Edit")) {
						id = ((Edit) value).getArg0();
						edit = true;
					} else if (value.toString().contains("Delete")) {
						id = ((Delete) value).getArg0();
						delete = true;
					} else
						return true; // GetAll or Create
					// If id > 0, there is no problem to fix on the client
					// side.
					if (id > 0)
						return true;
					// If the request is GetOne, Edit, or Delete and the id
					// is
					// zero, // there is a problem that cannot be fixed.
					if (getOne || edit || delete) {
						if (id == 0) // can't fix
							throw new RuntimeException("ID cannot be zero!");
						// id < 0 and operation is GetOne, Edit, or Delete
						int newId = Math.abs(id);
						// Update argument.
						if (getOne)
							((GetOne) value).setArg0(newId);
						else if (edit)
							((Edit) value).setArg0(newId);
						else if (delete)
							((Delete) value).setArg0(newId); // Update
																// payload.
						((JAXBElement) payload).setValue(value);
						// Update message
						msg.setPayload(payload, jaxbCtx);
					}
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return true;
	}
}