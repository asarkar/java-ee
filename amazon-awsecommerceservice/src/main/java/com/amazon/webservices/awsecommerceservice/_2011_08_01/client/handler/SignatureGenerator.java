package com.amazon.webservices.awsecommerceservice._2011_08_01.client.handler;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import javax.xml.ws.WebServiceException;

import org.apache.log4j.Logger;

import edu.certification.abhijitsarkar.ocewsd.utility.logging.AppLogger;

public class SignatureGenerator {
	public SignatureGenerator() {
		this(DEFAULT_ALGORITHM, DEFAULT_CHARSET);
	}

	public SignatureGenerator(String algorithm, String charset) {
		this.algorithm = algorithm;
		this.charset = charset;
	}

	protected String getSignature(String operation, String timestamp,
			String secretAccessKey) {
		String signature = null;
		try {
			String toSign = operation + timestamp;
			byte[] toSignBytes = toSign.getBytes(charset);

			Mac signer = Mac.getInstance(algorithm);
			SecretKeySpec keySpec = new SecretKeySpec(
					secretAccessKey.getBytes(charset), algorithm);

			signer.init(keySpec);
			signer.update(toSignBytes);
			byte[] signBytes = signer.doFinal();

			signature = DatatypeConverter.printBase64Binary(signBytes);
		} catch (Exception e) {
			logger.error("Unable to create the signature", e);
			throw new WebServiceException("Unable to create the signature", e);
		}
		return signature;
	}

	private static final String DEFAULT_ALGORITHM = "HmacSHA256";
	private static final String DEFAULT_CHARSET = "UTF-8";
	private static final Logger logger = AppLogger
			.getInstance(SignatureGenerator.class);

	private String algorithm;
	private String charset;
}