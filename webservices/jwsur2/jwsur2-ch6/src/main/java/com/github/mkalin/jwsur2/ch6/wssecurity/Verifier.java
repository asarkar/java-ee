package com.github.mkalin.jwsur2.ch6.wssecurity;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import com.sun.xml.wss.impl.callback.PasswordValidationCallback;

// Verifier handles service-side callbacks for password validation.
public class Verifier implements CallbackHandler {

	// Username/password hard-coded for simplicity and clarity.
	private static final String _username = "fred";
	private static final String _password = "rockbed";

	// For password validation, set the validator to the inner class below.
	public void handle(Callback[] callbacks)
			throws UnsupportedCallbackException {
		for (int i = 0; i < callbacks.length; i++) {
			if (callbacks[i] instanceof PasswordValidationCallback) {
				PasswordValidationCallback cb = (PasswordValidationCallback) callbacks[i];
				if (cb.getRequest() instanceof PasswordValidationCallback.PlainTextPasswordRequest)
					cb.setValidator(new PlainTextPasswordVerifier());
			} else
				throw new UnsupportedCallbackException(null, "Not needed");
		}
	}

	// Encapsulated validate method verifies the username/password.
	private class PlainTextPasswordVerifier implements
			PasswordValidationCallback.PasswordValidator {

		public boolean validate(PasswordValidationCallback.Request req)
				throws PasswordValidationCallback.PasswordValidationException {

			PasswordValidationCallback.PlainTextPasswordRequest plain_pwd = (PasswordValidationCallback.PlainTextPasswordRequest) req;
			if (_username.equals(plain_pwd.getUsername())
					&& _password.equals(plain_pwd.getPassword())) {
				return true;
			} else
				return false;
		}
	}
}
