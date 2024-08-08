package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions.IllegalParameterException;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions.RabobankSdkException;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;

/**
 * An instance of this class is returned once the customer has paid, it contains
 * the orderId to specify the order of interest, and also a status to show if the payment was successful or not
 */
public class PaymentCompletedResponse extends SignedResponse {

    private static final Pattern ORDER_ID_PATTERN = Pattern.compile("\\p{Alnum}{1,24}");
    private static final Pattern STATUS_PATTERN = Pattern.compile("[A-Z_]{1,20}");
    private static final Pattern SIGNATURE_PATTERN = Pattern.compile("[0-9a-f]{128}");

    private final String orderId;
    private final String status;

    /**
     * Make use of {@link PaymentCompletedResponse#newPaymentCompletedResponse(String, String, String, byte[])} instead as this constructor does not validate the signature.
     * @param orderId    The order ID as received by the Rabobank OmniKassa.
     * @param status     The order status as received by the Rabobank OmniKassa.
     * @param signature  The signature of the response as received by the Rabobank OmniKassa.
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
        validateParameters(orderId, status, signature);
        PaymentCompletedResponse response = new PaymentCompletedResponse(orderId, status, signature);
        response.validateSignature(signingKey);
        return response;
    }

    private static void validateParameters(String orderId, String status, String signature) throws IllegalParameterException {
        if (isNull(orderId) || !ORDER_ID_PATTERN.matcher(orderId).matches()) {
            throw new IllegalParameterException("Invalid value for orderId");
        }
        if (isNull(status) || !STATUS_PATTERN.matcher(status).matches()) {
            throw new IllegalParameterException("Invalid value for status");
        }
        if (isNull(signature) || !SIGNATURE_PATTERN.matcher(signature).matches()) {
            throw new IllegalParameterException("Invalid value for signature");
        }
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
