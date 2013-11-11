package name.abhijitsarkar.learning.webservices.jaxws.security.client.sym;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

public class CalculatorSymCallbackHandler implements CallbackHandler {

	@Override
	public void handle(Callback[] callbacks) throws IOException,
			UnsupportedCallbackException {
		for (int i = 0; i < callbacks.length; i++) {
			System.out.println("Callback instance: "
					+ callbacks[i].getClass().getName());
			
			throw new IOException("I've been called");
		}
	}
}