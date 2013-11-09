package com.github.mkalin.jwsur2.ch6.tempconvert.client;

import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.ws.BindingProvider;

import org.apache.commons.codec.digest.DigestUtils;

import com.github.mkalin.jwsur2.ch6.tempconvert.client.generated.TempConvert;
import com.github.mkalin.jwsur2.ch6.tempconvert.client.generated.TempConvertService;

public class TempConvertClient2 {
	private static final String endpoint = "https://localhost:8443/ch6/tc";

	static {
		HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
			public boolean verify(String name, SSLSession session) {
				return true; // allow everything
			}
		});
		try {
			TrustManager[] trustMgr = new TrustManager[] { new X509TrustManager() {
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(X509Certificate[] cs, String t) {
				}

				public void checkServerTrusted(X509Certificate[] cs, String t) {
				}
			} };
			SSLContext sslCtx = SSLContext.getInstance("TLS");
			sslCtx.init(null, trustMgr, null);
			HttpsURLConnection.setDefaultSSLSocketFactory(sslCtx
					.getSocketFactory());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String args[]) throws Exception {
		if (args.length < 2) {
			System.err.println("Usage: TempConvertClient <uname> <passwd>");
			return;
		}
		String uname = args[0];
		String passwd = args[1];
		String passwdHash = DigestUtils.sha1Hex(passwd.getBytes("utf8"));

		// RealmBase.Digest(passwd, // password
		// "SHA", // algorithm
		// null); // default char encoding: utf-8

		// For a secure service like this one, a stand-alone client
		// won't be able to get to the WSDL in order to parse it.
		// Thus a locally saved WSDL needs to be used for wsimport code gen
		TempConvertService service = new TempConvertService();
		TempConvert port = service.getTempConvertPort();
		BindingProvider prov = (BindingProvider) port;

		prov.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
				endpoint);
		prov.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, uname);
		prov.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY,
				passwdHash);

		System.out.println("f2c(-40.1) = " + port.f2C(-40.1f));
		System.out.println("c2f(-40.1) = " + port.c2F(-40.1f));
		System.out.println("f2c(+98.7) = " + port.f2C(+98.7f));
	}
}
