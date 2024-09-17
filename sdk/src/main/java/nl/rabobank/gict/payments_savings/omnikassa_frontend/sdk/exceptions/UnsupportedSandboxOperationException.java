package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions;

public class UnsupportedSandboxOperationException extends RabobankSdkException{
    public UnsupportedSandboxOperationException() {
        super("This request is not supported for SANDBOX environment");
    }
}
