package com.github.mkalin.jwsur2.ch6.https;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.Certificate;

import javax.net.ssl.HttpsURLConnection;

public class GoogleClient {
	private static final String endpoint = "https://www.google.com:443/";

	// Send a GET request and print the response status code.
	public static void main(String[] args) {
		new GoogleClient().doIt();
	}

	private void doIt() {
		try {
			URL url = new URL(endpoint);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod("GET");
			conn.connect();
			dumpDetails(conn);
		} catch (MalformedURLException e) {
			System.err.println(e);
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	private void dumpDetails(HttpsURLConnection conn) {
		try {
			print("Status code:  " + conn.getResponseCode());
			print("Cipher suite: " + conn.getCipherSuite());
			Certificate[] certs = conn.getServerCertificates();
			for (Certificate cert : certs) {
				print("\tCert. type: " + cert.getType());
				print("\tHash code:  " + cert.hashCode());
				print("\tAlgorithm:  " + cert.getPublicKey().getAlgorithm());
				print("\tFormat:     " + cert.getPublicKey().getFormat());
				print("");
			}

		} catch (Exception e) {
			System.err.println(e);
		}
	}

	private void print(Object s) {
		System.out.println(s);
	}
}
