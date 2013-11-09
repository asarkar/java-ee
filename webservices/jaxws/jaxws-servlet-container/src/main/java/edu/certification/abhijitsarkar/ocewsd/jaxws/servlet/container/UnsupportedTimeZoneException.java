package edu.certification.abhijitsarkar.ocewsd.jaxws.servlet.container;

public class UnsupportedTimeZoneException extends Exception {

	private static final long serialVersionUID = 1L;

	public UnsupportedTimeZoneException() {
		super();
	}

	public UnsupportedTimeZoneException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public UnsupportedTimeZoneException(String message) {
		super(message);
	}

	public UnsupportedTimeZoneException(Throwable throwable) {
		super(throwable);
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
