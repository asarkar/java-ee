package org.digester;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;

import org.apache.commons.codec.binary.Base64;

/**
 * 
 * @author adam-bien.com
 */
@Provider
public class PayloadVerifier implements ReaderInterceptor, WriterInterceptor {

	private static final Logger LOG = Logger.getLogger(PayloadVerifier.class
			.getName());
	public static final String SIGNATURE_HEADER = "x-signature";

	@Override
	public Object aroundReadFrom(ReaderInterceptorContext ric)
			throws IOException, WebApplicationException {
		MultivaluedMap<String, String> headers = ric.getHeaders();
		String headerSignagure = headers.getFirst(SIGNATURE_HEADER);
		InputStream inputStream = ric.getInputStream();
		byte[] content = fetchBytes(inputStream);
		String payload = computeFingerprint(content);
		LOG.log(Level.INFO, "Header: {0} Payload: {1}", new Object[] {
				headerSignagure, payload });
		if (!payload.equals(headerSignagure)) {
			LOG.log(Level.WARNING, "Signatures don''t match: {0}<->{1}",
					new Object[] { headerSignagure, payload });
			Response response = Response.status(Response.Status.BAD_REQUEST)
					.header(SIGNATURE_HEADER, "Modified content").build();
			throw new WebApplicationException(response);
		}
		ByteArrayInputStream buffer = new ByteArrayInputStream(content);
		ric.setInputStream(buffer);
		return ric.proceed();

	}

	@Override
	public void aroundWriteTo(WriterInterceptorContext wic) throws IOException,
			WebApplicationException {
		OutputStream oos = wic.getOutputStream();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		wic.setOutputStream(baos);
		wic.proceed();
		baos.flush();
		byte[] content = baos.toByteArray();
		MultivaluedMap<String, Object> headers = wic.getHeaders();
		headers.add(SIGNATURE_HEADER, computeFingerprint(content));
		oos.write(content);

	}

	public String computeFingerprint(byte[] content) {
		return Base64.encodeBase64String(getDigester().digest(content));
	}

	public byte[] fetchBytes(InputStream is) throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int read;
		byte[] data = new byte[1024];
		while ((read = is.read(data, 0, data.length)) != -1) {
			buffer.write(data, 0, read);
		}
		buffer.flush();
		return buffer.toByteArray();
	}

	public MessageDigest getDigester() {
		try {
			return MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException ex) {
			throw new IllegalStateException("Algorithm not found", ex);
		}
	}
}
