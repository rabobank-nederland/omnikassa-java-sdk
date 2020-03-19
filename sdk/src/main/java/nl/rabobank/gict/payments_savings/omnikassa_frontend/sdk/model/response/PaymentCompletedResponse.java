package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions.RabobankSdkException;

import java.util.Arrays;
import java.util.List;

/**
 * An instance of this class is returned once the customer has paid, it contains
 * the orderId to specify the order of interest, and also a status to show if the payment was successful or not
 */
public class PaymentCompletedResponse extends SignedResponse {

    private final String orderId;
    private final String status;

    /**
     * Make use of {@link PaymentCompletedResponse#newPaymentCompletedResponse(String, String, String, byte[])} instead as this constructor does not validate the signature.
     */
    @Deprecated
    public PaymentCompletedResponse(String orderId, String status, String signature) {
        super(signature);
        this.orderId = orderId;
        this.status = status;
    }

    /**
     * Create a new instance of the {@link PaymentCompletedResponse} and validate the signature.
     *
     * @param orderId    The order ID as received by the Rabobank OmniKassa.
     * @param status     The order status as received by the Rabobank OmniKassa.
     * @param signature  The signature of the response as received by the Rabobank OmniKassa.
     * @param signingKey The signing key to validate the signature with.
     * @return a new payment completed response instance.
     * @throws RabobankSdkException when the signature is invalid.
     */
    public static PaymentCompletedResponse newPaymentCompletedResponse(String orderId,
                                                                       String status,
                                                                       String signature,
                                                                       byte[] signingKey) throws RabobankSdkException {
        PaymentCompletedResponse response = new PaymentCompletedResponse(orderId, status, signature);
        response.validateSignature(signingKey);
        return response;
    }

    public String getStatus() {
        return status;
    }

    public String getOrderID() {
        return orderId;
    }

    @Override
    public List<String> getSignatureData() {
        return Arrays.asList(orderId, status);
    }
}
