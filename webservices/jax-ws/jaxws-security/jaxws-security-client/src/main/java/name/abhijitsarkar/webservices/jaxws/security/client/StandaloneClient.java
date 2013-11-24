package name.abhijitsarkar.webservices.jaxws.security.client;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import name.abhijitsarkar.webservices.jaxws.security.ejb.CalculatorEJBRemote;

// This is how a standalone GF client works. This class does not work though;
// Irrelevant at this point.
public class StandaloneClient {

	private static final String GLOBAL_JNDI_NAME = "java:global/"
			+ "jaxws-security/calculator-ejb-0.0.1-SNAPSHOT/"
			+ "CalculatorEJB!"
			+ "name.abhijitsarkar.webservices.jaxws.security.ejb.CalculatorEJBRemote";

	public static void main(String[] args) throws Exception {
		System.out.println(new StandaloneClient().addUsingCalculatorEJB(1, 2));
	}

	private int addUsingCalculatorEJB(int i, int j) throws Exception {
		return getRemoteInterface().add(1, 2);
	}

	private CalculatorEJBRemote getRemoteInterface() throws Exception {
		return (CalculatorEJBRemote) getContext().lookup(GLOBAL_JNDI_NAME);
	}

	private Context getContext() throws Exception {
		final Properties jndiProperties = new Properties();
		jndiProperties.load(getClass().getResourceAsStream("/jndi.properties"));
		return new InitialContext(jndiProperties);
	}
}
