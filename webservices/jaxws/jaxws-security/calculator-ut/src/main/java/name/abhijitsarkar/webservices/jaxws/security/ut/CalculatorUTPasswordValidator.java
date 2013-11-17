package name.abhijitsarkar.webservices.jaxws.security.ut;

import name.abhijitsarkar.util.logging.AppLogger;

import org.apache.log4j.Logger;

import com.sun.xml.wss.impl.callback.PasswordValidationCallback.PasswordValidationException;
import com.sun.xml.wss.impl.callback.PasswordValidationCallback.PasswordValidator;
import com.sun.xml.wss.impl.callback.PasswordValidationCallback.PlainTextPasswordRequest;
import com.sun.xml.wss.impl.callback.PasswordValidationCallback.Request;

public class CalculatorUTPasswordValidator implements PasswordValidator {
	private static final Logger logger = AppLogger
			.getInstance(CalculatorUTPasswordValidator.class);

	@Override
	public boolean validate(Request request) throws PasswordValidationException {
		if (!(request instanceof PlainTextPasswordRequest)) {
			logger.error("Expected PlainTextPasswordRequest, got "
					+ request.getClass().getName());
			throw new PasswordValidationException(
					"Expected PlainTextPasswordRequest, got "
							+ request.getClass().getName());
		}

		PlainTextPasswordRequest plainTextRequest = (PlainTextPasswordRequest) request;

		if ("abhijit".equals(plainTextRequest.getUsername())
				&& "password".equals(plainTextRequest.getPassword())) {
			logger.debug("Username and password match found!");
			return true;
		}

		logger.error("Invalid credentials.");

		throw new PasswordValidationException("Invalid credentials.");
	}
}
