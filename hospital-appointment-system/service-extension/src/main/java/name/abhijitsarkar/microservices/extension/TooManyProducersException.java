package name.abhijitsarkar.microservices.extension;

public class TooManyProducersException extends RuntimeException {
    private static final long serialVersionUID = -4126875484290732670L;

    public TooManyProducersException() {
	super();
    }

    public TooManyProducersException(String message, Throwable cause,
	    boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }

    public TooManyProducersException(String message, Throwable cause) {
	super(message, cause);
    }

    public TooManyProducersException(String message) {
	super(message);
    }

    public TooManyProducersException(Throwable cause) {
	super(cause);
    }
}
