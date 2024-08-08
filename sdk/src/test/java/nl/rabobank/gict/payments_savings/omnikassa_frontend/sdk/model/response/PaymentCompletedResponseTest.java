package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response;

import org.junit.Test;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions.IllegalParameterException;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions.IllegalSignatureException;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions.RabobankSdkException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.PaymentCompletedResponse.newPaymentCompletedResponse;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

public class PaymentCompletedResponseTest {
    private static final String ORDER_ID = "ORDER1";
    private static final String STATUS = "COMPLETED";
    private static final String SIGNATURE = "6f4911ff3a77e0d5a323f91aad58bef2cdb4a8f0efc62d2fd4b82ca68d13ce4df5a4020ce390eff5a211551c6134b3fc02c750de9dc7bf04619f8bcfdd12d7c3";
    private static final byte[] SIGNING_KEY = getBytes("secret");

    @Test
    public void shouldBeAbleToCreateNewInstance() throws Exception {
        PaymentCompletedResponse response = makeResponse(SIGNATURE, SIGNING_KEY);

        assertThat(response.getOrderID(), is(ORDER_ID));
        assertThat(response.getStatus(), is(STATUS));
    }

    @Test(expected = IllegalSignatureException.class)
    public void shouldNotAcceptCryptographicallyIncorrectSignature() throws Exception {
        makeResponse("00" + SIGNATURE.substring(2), SIGNING_KEY);
    }

    @Test(expected = IllegalSignatureException.class)
    public void shouldNotAcceptInvalidSigningKey() throws Exception {
        makeResponse(SIGNATURE, getBytes("bad"));
    }

    @Test
    public void shouldNotAcceptInvalidOrderIds() throws Exception {
        shouldRejectInvalidOrderId("123<script>456");
        shouldRejectInvalidOrderId("1234567890123456789012345");
        shouldRejectInvalidOrderId("");
        shouldRejectInvalidOrderId(null);
    }

    private void shouldRejectInvalidOrderId(String orderId) throws Exception {
        try {
            newPaymentCompletedResponse(orderId, STATUS, SIGNATURE, SIGNING_KEY);
            fail("IllegalParameterException expected");
        } catch (IllegalParameterException ignored) {
        }
    }

    @Test
    public void shouldNotAcceptInvalidStatus() throws Exception {
        shouldRejectInvalidStatus("IN_<script>PROGRESS");
        shouldRejectInvalidStatus("AAAAAAAAAAAAAAAAAAAAA");
        shouldRejectInvalidStatus("");
        shouldRejectInvalidStatus(null);
    }

    private void shouldRejectInvalidStatus(String status) throws Exception {
        try {
            newPaymentCompletedResponse(ORDER_ID, status, SIGNATURE, SIGNING_KEY);
            fail("IllegalParameterException expected");
        } catch (IllegalParameterException ignored) {
        }
    }

    @Test
    public void shouldNotAcceptInvalidSignature() throws Exception {
        shouldRejectInvalidSignature("6f4911ff3a77e0d5a323f91aad58bef2<script>efc62d2fd4b82ca68d13ce4df5a4020ce390eff5a211551c6134b3fc02c750de9dc7bf04619f8bcfdd12d7c3");
        shouldRejectInvalidSignature(SIGNATURE + "f5");
        shouldRejectInvalidSignature(SIGNATURE.substring(2));
        shouldRejectInvalidSignature("");
        shouldRejectInvalidSignature(null);
    }

    private void shouldRejectInvalidSignature(String signature) throws Exception {
        try {
            newPaymentCompletedResponse(ORDER_ID, STATUS, signature, SIGNING_KEY);
            fail("IllegalParameterException expected");
        } catch (IllegalParameterException ignored) {
        }
    }

    @Test
    public void shouldReturnCorrectSignatureData() throws Exception {
        PaymentCompletedResponse response = makeResponse(SIGNATURE, SIGNING_KEY);

        assertThat(response.getSignatureData(), hasItems(ORDER_ID, STATUS));
    }

    private PaymentCompletedResponse makeResponse(String signature, byte[] signingKey) throws RabobankSdkException {
        return newPaymentCompletedResponse(ORDER_ID, STATUS, signature, signingKey);
    }

    private static byte[] getBytes(String value) {
        return value.getBytes(UTF_8);
    }
}
