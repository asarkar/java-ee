package com.github.mkalin.jwsur2.ch6.wssecurity.client;

import java.security.cert.X509Certificate;
import java.util.LinkedList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;

import com.github.mkalin.jwsur2.ch6.wssecurity.client.generated.Echo;
import com.github.mkalin.jwsur2.ch6.wssecurity.client.generated.EchoService;

public class EchoClientWSS {
	static {
		HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
			public boolean verify(String name, SSLSession session) {
				return true;
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

	public static void main(String[] args) {

		try {
			@SuppressWarnings("rawtypes")
			List<Handler> chain = new LinkedList<Handler>();
			chain.add(new ClientHandler());

			EchoService service = new EchoService();
			Echo port = service.getEchoPort();
			Binding binding = ((BindingProvider) port).getBinding();
			binding.setHandlerChain(chain);
			BindingProvider prov = (BindingProvider) port;

			prov.getRequestContext().put(BindingProvider.USERNAME_PROPERTY,
					"moe");
			prov.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY,
					"MoeMoeMoe");

			String response = port.echo("Goodbye, cruel world!");
			System.out.println("From Echo service: " + response);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
