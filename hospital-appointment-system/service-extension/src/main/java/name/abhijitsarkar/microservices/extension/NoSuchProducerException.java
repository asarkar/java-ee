package name.abhijitsarkar.microservices.extension;

public class NoSuchProducerException extends RuntimeException {
    private static final long serialVersionUID = -4126875484290732670L;

    public NoSuchProducerException() {
	super();
    }

    public NoSuchProducerException(String message, Throwable cause,
	    boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }

    public NoSuchProducerException(String message, Throwable cause) {
	super(message, cause);
    }

    public NoSuchProducerException(String message) {
	super(message);
    }

    public NoSuchProducerException(Throwable cause) {
	super(cause);
    }
}
