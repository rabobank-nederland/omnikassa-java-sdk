package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.connector;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions.IllegalApiResponseException;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions.IllegalSignatureException;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions.InvalidAccessTokenException;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions.RabobankSdkException;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.AccessToken;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.AccessTokenBuilder;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.MerchantOrderTestFactory;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.PaymentBrand;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.TransactionStatus;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.TransactionType;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.request.MerchantOrderRequest;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.ApiNotification;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.ApiNotificationBuilder;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.MerchantOrderResponse;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.MerchantOrderResponseBuilder;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.MerchantOrderResult;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.MerchantOrderStatusResponse;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.MerchantOrderStatusResponseBuilder;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.PaymentBrandsResponse;

import java.math.BigDecimal;
import java.util.UUID;

import static java.nio.charset.StandardCharsets.UTF_8;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions.InvalidAccessTokenException.INVALID_AUTHORIZATION_ERROR_CODE;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.Currency.EUR;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.utils.CalendarUtils.calendarToString;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.utils.CalendarUtils.stringToCalendar;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ApiConnectorTest {
    private static final byte[] SIGNING_KEY = "secret".getBytes(UTF_8);
    private static final String ORDER_SERVER_API_ORDER = "order/server/api/v2/order";
    private static final String ORDER_SERVER_API_EVENTS_RESULTS_EVENT = "order/server/api/v2/events/results/event";
    private static final String ACTIVE = "Active";
    private static final String INACTIVE = "InActive";

    @Mock
    private UnirestJSONTemplate jsonTemplate;

    private ApiConnector classUnderTest;

    @Before
    public void setUp() {
        classUnderTest = new ApiConnector(jsonTemplate, SIGNING_KEY);
    }

    @Test
    public void testAnnounceMerchantOrder_HappyFlow() throws Exception {
        MerchantOrderRequest merchantOrderRequest = createMerchantOrderRequest();

        when(jsonTemplate.post(ORDER_SERVER_API_ORDER, merchantOrderRequest, "token")).thenReturn(prepareMerchantOrderResponse());

        MerchantOrderResponse merchantOrderResponse = classUnderTest.announceMerchantOrder(merchantOrderRequest, "token");

        assertThat(merchantOrderResponse.getRedirectUrl(), is("http://returnAdress"));
        assertThat(merchantOrderResponse.getOmnikassaOrderId(), is(UUID.fromString("25da863a-60a5-475d-ae47-c0e4bd1bec31")));
    }

    @Test(expected = RabobankSdkException.class)
    public void testAnnounceMerchantOrder_UniRestException() throws Exception {
        MerchantOrderRequest merchantOrderRequest = createMerchantOrderRequest();

        when(jsonTemplate.post(ORDER_SERVER_API_ORDER, merchantOrderRequest, "token")).thenThrow(new UnirestException("UniREST test"));

        classUnderTest.announceMerchantOrder(merchantOrderRequest, "token");
    }

    @Test
    public void testAnnounceMerchantOrder_ApiReturnsError() throws Exception {
        try {
            MerchantOrderRequest merchantOrderRequest = createMerchantOrderRequest();

            when(jsonTemplate.post(ORDER_SERVER_API_ORDER, merchantOrderRequest, "token")).thenReturn(prepareErrorResponse());

            classUnderTest.announceMerchantOrder(merchantOrderRequest, "token");
            fail();
        } catch (IllegalApiResponseException exception) {
            assertThat(exception.getErrorCode(), is(5002));
            assertThat(exception.getErrorMessage(), is("merchantOrderId should only contain alphanumeric characters"));
        }
    }

    @Test(expected = IllegalApiResponseException.class)
    public void testAnnounceMerchantOrder_ApiReturnsUnexpectedError() throws Exception {
        MerchantOrderRequest merchantOrderRequest = createMerchantOrderRequest();

        when(jsonTemplate.post(ORDER_SERVER_API_ORDER, merchantOrderRequest, "token")).thenReturn(prepareUnexpectedErrorResponse());

        classUnderTest.announceMerchantOrder(merchantOrderRequest, "token");
    }

    private static MerchantOrderRequest createMerchantOrderRequest() throws RabobankSdkException {
        return new MerchantOrderRequest(MerchantOrderTestFactory.any());
    }

    @Test
    public void testGetAnnouncementData_HappyFlow() throws Exception {
        ApiNotification apiNotification = new ApiNotificationBuilder().build();

        when(jsonTemplate.get(ORDER_SERVER_API_EVENTS_RESULTS_EVENT, apiNotification.getAuthentication())).thenReturn(prepareMerchantOrderStatusResponse());

        MerchantOrderStatusResponse merchantOrderStatusResponse = classUnderTest.getAnnouncementData(apiNotification);

        assertThat(merchantOrderStatusResponse.moreOrderResultsAvailable(), is(false));
        assertThat(merchantOrderStatusResponse.getOrderResults().size(), is(2));

        assertFirstMerchantOrderResult(merchantOrderStatusResponse.getOrderResults().get(0));
        assertSecondMerchantOrderResult(merchantOrderStatusResponse.getOrderResults().get(1));
    }

    @Test
    public void testRetrievePaymentBrandOk() throws Exception {
        when(jsonTemplate.get("order/server/api/payment-brands", "accessToken")).thenReturn(createPaymentBrandResponse());

        PaymentBrandsResponse paymentBrandsResponse = classUnderTest.retrievePaymentBrands("accessToken");
        assertThat(paymentBrandsResponse.getPaymentBrands().size(), is(2));
        assertThat(paymentBrandsResponse.getPaymentBrands().get(0).getName(), is("IDEAL"));
        assertThat(paymentBrandsResponse.getPaymentBrands().get(1).getName(), is("MASTERCARD"));
        assertThat(paymentBrandsResponse.getPaymentBrands().get(0).isActive(), is(true));
        assertThat(paymentBrandsResponse.getPaymentBrands().get(1).isActive(), is(false));
    }

    @Test(expected = RabobankSdkException.class)
    public void testRetrievePaymentBrand_UniRestException() throws Exception {
        when(jsonTemplate.get("order/server/api/payment-brands", "accessToken")).thenThrow(new UnirestException("UniREST test"));

        classUnderTest.retrievePaymentBrands("accessToken");
    }

    private void assertFirstMerchantOrderResult(MerchantOrderResult actual) {
        assertThat(actual.getPointOfInteractionId(), is(1));
        assertThat(actual.getMerchantOrderId(), is("MYSHOP0001"));
        assertThat(actual.getOmnikassaOrderId(), is("aec58605-edcf-4886-b12d-594a8a8eea60"));
        assertThat(actual.getErrorCode(), is(""));
        assertThat(actual.getOrderStatus(), is("FAILURE"));
        assertThat(actual.getOrderStatusDateTime(), is(stringToCalendar("2016-07-28T12:51:15.574+02:00")));
        assertThat(actual.getPaidAmount().getCurrency(), is(EUR));
        assertThat(actual.getPaidAmount().getAmountInCents(), is(0L));
        assertThat(actual.getTotalAmount().getCurrency(), is(EUR));
        assertThat(actual.getTotalAmount().getAmountInCents(), is(599L));
        assertThat(actual.getTransactionInfo().get(0).getId(), is("1"));
        assertThat(actual.getTransactionInfo().get(0).getPaymentBrand(), is(PaymentBrand.IDEAL));
        assertThat(actual.getTransactionInfo().get(0).getType(), is(TransactionType.PAYMENT));
        assertThat(actual.getTransactionInfo().get(0).getStatus(), is(TransactionStatus.SUCCESS));
        assertThat(actual.getTransactionInfo().get(0).getAmount().getAmount(), is(new BigDecimal("5.99")));
        assertThat(actual.getTransactionInfo().get(0).getAmount().getCurrency(), is(EUR));
        assertThat(actual.getTransactionInfo().get(0).getConfirmedAmount().getAmount(), is(new BigDecimal("5.99")));
        assertThat(actual.getTransactionInfo().get(0).getConfirmedAmount().getCurrency(), is(EUR));
        assertThat(actual.getTransactionInfo().get(0).getStartTime(), is("2016-07-28T12:51:15.574+02:00"));
        assertThat(actual.getTransactionInfo().get(0).getLastUpdateTime(), is("2016-07-28T12:51:15.574+02:00"));
    }

    private void assertSecondMerchantOrderResult(MerchantOrderResult actual) {
        assertThat(actual.getPointOfInteractionId(), is(1));
        assertThat(actual.getMerchantOrderId(), is("MYSHOP0002"));
        assertThat(actual.getOmnikassaOrderId(), is("e516e630-9713-4cfa-ae88-c5fbc4b06744"));
        assertThat(actual.getErrorCode(), is(""));
        assertThat(actual.getOrderStatus(), is("COMPLETED"));
        assertThat(actual.getOrderStatusDateTime(), is(stringToCalendar("2016-07-28T13:58:50.205+02:00")));
        assertThat(actual.getPaidAmount().getCurrency(), is(EUR));
        assertThat(actual.getPaidAmount().getAmountInCents(), is(599L));
        assertThat(actual.getTotalAmount().getCurrency(), is(EUR));
        assertThat(actual.getTotalAmount().getAmountInCents(), is(599L));
        assertThat(actual.getTransactionInfo().get(0).getId(), is("1"));
        assertThat(actual.getTransactionInfo().get(0).getPaymentBrand(), is(PaymentBrand.IDEAL));
        assertThat(actual.getTransactionInfo().get(0).getType(), is(TransactionType.PAYMENT));
        assertThat(actual.getTransactionInfo().get(0).getStatus(), is(TransactionStatus.SUCCESS));
        assertThat(actual.getTransactionInfo().get(0).getAmount().getAmount(), is(new BigDecimal("5.99")));
        assertThat(actual.getTransactionInfo().get(0).getAmount().getCurrency(), is(EUR));
        assertThat(actual.getTransactionInfo().get(0).getConfirmedAmount().getAmount(), is(new BigDecimal("5.99")));
        assertThat(actual.getTransactionInfo().get(0).getConfirmedAmount().getCurrency(), is(EUR));
        assertThat(actual.getTransactionInfo().get(0).getStartTime(), is("2016-07-28T12:51:15.574+02:00"));
        assertThat(actual.getTransactionInfo().get(0).getLastUpdateTime(), is("2016-07-28T12:51:15.574+02:00"));
    }

    @Test(expected = IllegalSignatureException.class)
    public void testGetAnnouncementData_InvalidSignature() throws Exception {
        ApiNotification apiNotification = new ApiNotificationBuilder().build();

        when(jsonTemplate.get(ORDER_SERVER_API_EVENTS_RESULTS_EVENT, apiNotification.getAuthentication())).thenReturn(prepareMerchantOrderStatusResponseInvalidSignature());

        classUnderTest.getAnnouncementData(apiNotification);
    }

    @Test(expected = RabobankSdkException.class)
    public void testGetAnnouncementData_UniRestException() throws Exception {
        ApiNotification apiNotification = new ApiNotificationBuilder().build();

        when(jsonTemplate.get(ORDER_SERVER_API_EVENTS_RESULTS_EVENT, apiNotification.getAuthentication())).thenThrow(new UnirestException("UniREST test"));

        classUnderTest.getAnnouncementData(apiNotification);
    }

    @Test(expected = IllegalApiResponseException.class)
    public void testGetAnnouncementData_ApiReturnsError() throws Exception {
        ApiNotification apiNotification = new ApiNotificationBuilder().build();

        when(jsonTemplate.get(ORDER_SERVER_API_EVENTS_RESULTS_EVENT, apiNotification.getAuthentication())).thenReturn(prepareErrorResponse());

        classUnderTest.getAnnouncementData(apiNotification);
    }

    @Test(expected = InvalidAccessTokenException.class)
    public void testGetAnnouncementData_ApiReturnsAuthenticationError() throws Exception {
        ApiNotification apiNotification = new ApiNotificationBuilder().build();

        when(jsonTemplate.get(ORDER_SERVER_API_EVENTS_RESULTS_EVENT, apiNotification.getAuthentication())).thenReturn(prepareAuthenticationErrorResponse());

        classUnderTest.getAnnouncementData(apiNotification);
    }

    @Test
    public void testRetrieveNewToken_HappyFlow() throws Exception {
        when(jsonTemplate.get("gatekeeper/refresh", "refreshtoken")).thenReturn(prepareAccessTokenResponse());

        AccessToken accessToken = classUnderTest.retrieveNewToken("refreshtoken");

        assertThat(accessToken.getToken(), is("token"));
        assertThat(calendarToString(accessToken.getValidUntil()), is("2016-09-22T10:10:04.848+0200"));
        assertThat(accessToken.getDurationInMillis(), is(28800000));
    }

    @Test(expected = RabobankSdkException.class)
    public void testRetrieveNewToken_UniRestException() throws Exception {
        when(jsonTemplate.get("gatekeeper/refresh", "refreshtoken")).thenThrow(new UnirestException("UniREST test"));

        classUnderTest.retrieveNewToken("refreshtoken");
    }

    @Test(expected = RabobankSdkException.class)
    public void testRetrieveNewToken_ApiReturnsError() throws Exception {
        when(jsonTemplate.get("gatekeeper/refresh", "refreshtoken")).thenThrow(new UnirestException("UniREST test"));

        classUnderTest.retrieveNewToken("refreshtoken");
    }

    private JSONObject prepareAccessTokenResponse() {
        return new AccessTokenBuilder().buildJsonObject();
    }

    private JSONObject prepareUnexpectedErrorResponse() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("errorCode", "0");
        return jsonObject;
    }

    private JSONObject prepareAuthenticationErrorResponse() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("errorCode", String.valueOf(INVALID_AUTHORIZATION_ERROR_CODE));
        jsonObject.put("message", "Full authentication is required to access this resource");
        return jsonObject;
    }

    private JSONObject prepareErrorResponse() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("errorCode", "5002");
        jsonObject.put("consumerMessage", "merchantOrderId should only contain alphanumeric characters");
        return jsonObject;
    }

    private JSONObject prepareMerchantOrderStatusResponseInvalidSignature() {
        return new MerchantOrderStatusResponseBuilder().withSignature("").buildJsonObject();
    }

    private JSONObject prepareMerchantOrderStatusResponse() {
        return new MerchantOrderStatusResponseBuilder().buildJsonObject();
    }

    private JSONObject prepareMerchantOrderResponse() {
        return new MerchantOrderResponseBuilder().withOmnikassaOrderId("25da863a-60a5-475d-ae47-c0e4bd1bec31").buildJsonObject();
    }

    private JSONObject createPaymentBrandResponse() {
        JSONArray jsonArray = new JSONArray();
        JSONObject paymentBrand1 = new JSONObject();
        paymentBrand1.put("name", "IDEAL");
        paymentBrand1.put("status", ACTIVE);

        JSONObject paymentBrand2 = new JSONObject();
        paymentBrand2.put("name", "MASTERCARD");
        paymentBrand2.put("status", INACTIVE);
        jsonArray.put(paymentBrand1);
        jsonArray.put(paymentBrand2);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("paymentBrands", jsonArray);

        return jsonObject;
    }
}
