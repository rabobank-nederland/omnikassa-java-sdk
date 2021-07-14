package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions;

/**
 * This exception is typically thrown when a parameter in a request contains an invalid value.
 */
public class IllegalParameterException extends RabobankSdkException {
    public IllegalParameterException(String message) {
        super(message);
    }

    public IllegalParameterException(Exception cause) {
        super(cause);
    }
}
