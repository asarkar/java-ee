package com.github.mkalin.jwsur2.ch6.predictions.https.client;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

import org.apache.commons.codec.binary.Base64;

public class PredictionsHttpsClientAA {
	private static final String endpoint = "https://localhost:8443/ch6/predictions3";
	private static final String truststore = "/META-INF/test.keystore";

	public static void main(String[] args) {
		new PredictionsHttpsClientAA().runTests();
	}

	private void runTests() {
		try {
			SSLContext sslCtx = SSLContext.getInstance("TLS");
			char[] password = "qubits".toCharArray();
			KeyStore ks = KeyStore.getInstance("JKS");
			InputStream is = getClass().getResourceAsStream(truststore);
			ks.load(is, password);
			TrustManagerFactory tmf = TrustManagerFactory
					.getInstance("SunX509");
			tmf.init(ks); // same as keystore
			sslCtx.init(null, // not needed, not challenged
					tmf.getTrustManagers(), new SecureRandom());

			HttpsURLConnection.setDefaultSSLSocketFactory(sslCtx
					.getSocketFactory());

			// Proof of concept tests.
			String uname = "moe";
			String passwd = "MoeMoeMoe";

			getTest(uname, passwd);
			postTest(uname, passwd);
			getTestAll(uname, passwd); // confirm POST test
			deleteTest(uname, passwd, "31");
			getTestAll(uname, passwd); // confirm DELETE test
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private HttpsURLConnection getConnection(URL url, String verb,
			String uname, String passwd) {
		try {
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod(verb);

			// authentication (although header name is Authorization)
			String userpass = uname + ":" + passwd;
			String basicAuth = "Basic "
					+ new String(new Base64().encode(userpass.getBytes()));
			conn.setRequestProperty("Authorization", basicAuth);

			// Guard against "bad hostname" errors during handshake.
			conn.setHostnameVerifier(new HostnameVerifier() {
				public boolean verify(String host, SSLSession session) {
					return host.equals("localhost"); // for development
				}
			});
			return conn;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void getTest(String uname, String passwd) {
		getTestAll(uname, passwd);
		getTestOne(uname, passwd, "31");
	}

	private void getTestAll(String uname, String passwd) {
		try {
			URL url = new URL(endpoint);
			HttpsURLConnection conn = getConnection(url, "GET", uname, passwd);
			conn.connect();
			readResponse("GET all request:\n", conn);
			conn.disconnect();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void getTestOne(String uname, String passwd, String id) {
		try {
			URL url = new URL(endpoint + "?id=" + id);
			HttpsURLConnection conn = getConnection(url, "GET", uname, passwd);
			conn.connect();
			readResponse("GET request for " + id + ":\n", conn);
			conn.disconnect();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void postTest(String uname, String passwd) {
		try {
			URL url = new URL(endpoint);
			HttpsURLConnection conn = getConnection(url, "POST", uname, passwd);
			conn.connect();
			writeBody(conn);
			readResponse("POST request:\n", conn);
			conn.disconnect();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void deleteTest(String uname, String passwd, String id) {
		try {
			URL url = new URL(endpoint + "?id=" + id);
			HttpsURLConnection conn = getConnection(url, "DELETE", uname,
					passwd);
			conn.connect();
			readResponse("DELETE request:\n", conn);
			conn.disconnect();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void writeBody(HttpsURLConnection conn) {
		try {
			String pairs = "who=Freddy&what=Avoid Friday nights if possible.";
			OutputStream out = conn.getOutputStream();
			out.write(pairs.getBytes());
			out.flush();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void readResponse(String msg, HttpsURLConnection conn) {
		try {
			byte[] buffer = new byte[4096];
			InputStream in = conn.getInputStream();
			ByteArrayOutputStream out = new ByteArrayOutputStream();

			int n = 0;
			// Append chunks to ByteArrayOutputStream.
			while ((n = in.read(buffer)) != -1)
				out.write(buffer, 0, n);
			in.close();

			System.out.println(new String(out.toByteArray())); // stringify and
																// print
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
