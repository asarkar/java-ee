package edu.certification.abhijitsarkar.ocewsd.ejb3.client;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.naming.remote.client.InitialContextFactory;

import edu.certification.abhijitsarkar.ocewsd.ejb3.SecureBeanRemote;

public class JBossEjb3Client {

	public String secureMethod(String username, String password) {
		SecureBeanRemote remote = getRemoteInterface(username, password);

		return (remote != null) ? remote.secureMethod() : null;
	}

	public String unsecureMethod(String username, String password) {
		SecureBeanRemote remote = getRemoteInterface(username, password);

		return (remote != null) ? remote.unsecureMethod() : null;
	}

	private Context getContext(final String username, final String password)
			throws NamingException {
		final Properties jndiProperties = new Properties();
		// jndiProperties.put(Context.URL_PKG_PREFIXES,
		// "org.jboss.ejb.client.naming");
		jndiProperties.put(Context.SECURITY_PRINCIPAL, username);
		jndiProperties.put(Context.SECURITY_CREDENTIALS, password);
		jndiProperties.put(Context.PROVIDER_URL, "remote://localhost:4447");
		jndiProperties.put("jboss.naming.client.ejb.context", true);
		jndiProperties
				.put("jboss.naming.client.connect.options.org.xnio.Options.SASL_POLICY_NOPLAINTEXT",
						"false");
		jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY,
				InitialContextFactory.class.getName());
		jndiProperties
				.put("jboss.naming.client.connect.options.org.xnio.Options.SASL_POLICY_NOPLAINTEXT",
						"false");
		return new InitialContext(jndiProperties);
	}

	private SecureBeanRemote getRemoteInterface(String username, String password) {
		try {
			return (SecureBeanRemote) getContext(username, password)
					.lookup("ejb3-security-1.0/SecureBean!edu.certification.abhijitsarkar.ocewsd.ejb3.SecureBeanRemote");
		} catch (NamingException e) {
			e.printStackTrace();
		}

		return null;
	}
}
