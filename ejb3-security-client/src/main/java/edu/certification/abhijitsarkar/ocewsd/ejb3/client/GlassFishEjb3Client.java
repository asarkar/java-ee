package edu.certification.abhijitsarkar.ocewsd.ejb3.client;

import java.io.IOException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import edu.certification.abhijitsarkar.ocewsd.ejb3.SecureBeanRemote;

public class GlassFishEjb3Client {
	public String secureMethod(String username, String password) {
		SecureBeanRemote remote = null;
		try {
			remote = getRemoteInterface(username, password);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return (remote != null) ? remote.secureMethod() : null;
	}

	public String unsecureMethod(String username, String password) {
		SecureBeanRemote remote = null;
		try {
			remote = getRemoteInterface(username, password);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return (remote != null) ? remote.unsecureMethod() : null;
	}

	private Context getContext(final String username, final String password)
			throws NamingException, IOException {
		final Properties jndiProperties = new Properties();
		jndiProperties.load(GlassFishEjb3Client.class
				.getResourceAsStream("/jndi.properties"));
		return new InitialContext(jndiProperties);
	}

	private SecureBeanRemote getRemoteInterface(String username, String password)
			throws IOException {
		try {
			return (SecureBeanRemote) getContext(username, password)
					.lookup("java:global/ejb3-security/SecureBean!edu.certification.abhijitsarkar.ocewsd.ejb3.SecureBeanRemote");
		} catch (NamingException e) {
			e.printStackTrace();
		}

		return null;
	}

}
