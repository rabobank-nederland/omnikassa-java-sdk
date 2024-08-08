package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions;

public class IllegalSignatureException extends RabobankSdkException {

    public IllegalSignatureException() {
        super("The signature validation of the response failed. Please contact the Rabobank service team.");
    }

}
