package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions;

public class RabobankSdkException extends Exception {
    public RabobankSdkException(String message) {
        super(message);
    }

    public RabobankSdkException(Exception cause) {
        super(cause);
    }
}
