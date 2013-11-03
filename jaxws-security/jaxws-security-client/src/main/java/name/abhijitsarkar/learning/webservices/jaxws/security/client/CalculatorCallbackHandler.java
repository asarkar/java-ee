package name.abhijitsarkar.learning.webservices.jaxws.security.client;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

public class CalculatorCallbackHandler implements CallbackHandler {

	@Override
	public void handle(Callback[] callbacks) throws IOException,
			UnsupportedCallbackException {
		for (int i = 0; i < callbacks.length; i++) {
			if (callbacks[i] instanceof NameCallback) {
				NameCallback nc = (NameCallback) callbacks[i];
				nc.setName("abhijit");
			} else if (callbacks[i] instanceof PasswordCallback) {
				PasswordCallback pc = (PasswordCallback) callbacks[i];
				pc.setPassword("password".toCharArray());
			} else {
				throw new UnsupportedCallbackException(callbacks[i],
						"Unsupported Callback");
			}
		}
	}
}
