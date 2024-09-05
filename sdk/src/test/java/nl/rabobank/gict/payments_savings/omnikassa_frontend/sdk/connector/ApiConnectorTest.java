package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.connector;

import kong.unirest.UnirestException;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
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
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.Currency;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.PaymentBrand;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.TransactionStatus;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.TransactionType;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.request.InitiateRefundRequest;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.request.MerchantOrderRequest;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.ApiNotification;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.ApiNotificationBuilder;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.MerchantOrderResponse;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.MerchantOrderResponseBuilder;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.MerchantOrderResult;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.MerchantOrderStatusResponse;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.MerchantOrderStatusResponseBuilder;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.OrderStatusResponse;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.OrderStatusResult;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.PaymentBrandsResponse;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.RefundDetailsResponse;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.TransactionRefundableDetailsResponse;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.utils.RefundTestFactory;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import static java.nio.charset.StandardCharsets.UTF_8;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions.InvalidAccessTokenException.INVALID_AUTHORIZATION_ERROR_CODE;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.Currency.EUR;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.utils.CalendarUtils.calendarToString;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.utils.CalendarUtils.stringToCalendar;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ApiConnectorTest {
    private static final Map<String, String> DEFAULT_USER_AGENT_HEADER = Collections.singletonMap("X-Api-User-Agent", "RabobankOmnikassaJavaSDK/1.14");
    private static final byte[] SIGNING_KEY = "secret".getBytes(UTF_8);
    private static final String ORDER_SERVER_API_ORDER = "order/server/api/v2/order";
    private static final String ORDER_SERVER_API_EVENTS_RESULTS_EVENT = "order/server/api/v2/events/results/event";
    private static final String API_REFUND_ENDPOINT = "order/server/api/v2/refund/transactions/";
    private static final String ACTIVE = "Active";
    private static final String INACTIVE = "InActive";

    @Mock
    private UnirestJSONTemplate jsonTemplate;

    private ApiConnector classUnderTest;

    @Before
    public void setUp() {
        classUnderTest = new ApiConnector(jsonTemplate, SIGNING_KEY, null, null);
    }

    @Test
    public void testAnnounceMerchantOrder_HappyFlowWithDefaultUserAgent() throws Exception {
        MerchantOrderRequest merchantOrderRequest = createMerchantOrderRequest();

        when(jsonTemplate.postWithHeader(ORDER_SERVER_API_ORDER, merchantOrderRequest, DEFAULT_USER_AGENT_HEADER, "token")).thenReturn(prepareMerchantOrderResponse());

        MerchantOrderResponse merchantOrderResponse = classUnderTest.announceMerchantOrder(merchantOrderRequest, "token");

        assertThat(merchantOrderResponse.getRedirectUrl(), is("http://returnAdress"));
        assertThat(merchantOrderResponse.getOmnikassaOrderId(), is(UUID.fromString("25da863a-60a5-475d-ae47-c0e4bd1bec31")));
    }

    @Test
    public void testAnnounceMerchantOrder_HappyFlowWithPartnerReference() throws Exception {
        MerchantOrderRequest merchantOrderRequest = createMerchantOrderRequest();

        when(jsonTemplate.postWithHeader(ORDER_SERVER_API_ORDER, merchantOrderRequest, Collections.singletonMap("X-Api-User-Agent", "RabobankOmnikassaJavaSDK/1.14 (pr: 12345)"), "token")).thenReturn(prepareMerchantOrderResponse());

        classUnderTest.setPartnerReference("12345");
        MerchantOrderResponse merchantOrderResponse = classUnderTest.announceMerchantOrder(merchantOrderRequest, "token");

        assertThat(merchantOrderResponse.getRedirectUrl(), is("http://returnAdress"));
        assertThat(merchantOrderResponse.getOmnikassaOrderId(), is(UUID.fromString("25da863a-60a5-475d-ae47-c0e4bd1bec31")));
    }

    @Test
    public void testAnnounceMerchantOrder_HappyFlowWithPartnerReferenceAndCustomUserAgent() throws Exception {
        MerchantOrderRequest merchantOrderRequest = createMerchantOrderRequest();

        when(jsonTemplate.postWithHeader(ORDER_SERVER_API_ORDER, merchantOrderRequest, Collections.singletonMap("X-Api-User-Agent", "RabobankOmnikassaJavaSDK/1.14 CustomAgent/1 (pr: 12345)"), "token")).thenReturn(prepareMerchantOrderResponse());

        classUnderTest.setPartnerReference("12345");
        classUnderTest.setUserAgent("CustomAgent/1");
        MerchantOrderResponse merchantOrderResponse = classUnderTest.announceMerchantOrder(merchantOrderRequest, "token");

        assertThat(merchantOrderResponse.getRedirectUrl(), is("http://returnAdress"));
        assertThat(merchantOrderResponse.getOmnikassaOrderId(), is(UUID.fromString("25da863a-60a5-475d-ae47-c0e4bd1bec31")));
    }

    @Test(expected = RabobankSdkException.class)
    public void testAnnounceMerchantOrder_UniRestException() throws Exception {
        MerchantOrderRequest merchantOrderRequest = createMerchantOrderRequest();

        when(jsonTemplate.postWithHeader(ORDER_SERVER_API_ORDER, merchantOrderRequest, DEFAULT_USER_AGENT_HEADER, "token")).thenThrow(new UnirestException("UniREST test"));

        classUnderTest.announceMerchantOrder(merchantOrderRequest, "token");
    }

    @Test
    public void testAnnounceMerchantOrder_ApiReturnsError() throws Exception {
        try {
            MerchantOrderRequest merchantOrderRequest = createMerchantOrderRequest();

            when(jsonTemplate.postWithHeader(ORDER_SERVER_API_ORDER, merchantOrderRequest, DEFAULT_USER_AGENT_HEADER, "token")).thenReturn(prepareErrorResponse());

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

        when(jsonTemplate.postWithHeader(ORDER_SERVER_API_ORDER, merchantOrderRequest, DEFAULT_USER_AGENT_HEADER, "token")).thenReturn(prepareUnexpectedErrorResponse());

        classUnderTest.announceMerchantOrder(merchantOrderRequest, "token");
    }

    private static MerchantOrderRequest createMerchantOrderRequest() throws RabobankSdkException {
        return new MerchantOrderRequest(MerchantOrderTestFactory.any());
    }

    @Test
    public void testPostRefundRequest_HappyFlow() throws Exception {
        InitiateRefundRequest initiateRefundRequest = RefundTestFactory.defaultInitiateRefundRequest();
        UUID transaction = UUID.randomUUID();
        UUID requestId = UUID.randomUUID();
        when(jsonTemplate.postWithHeader(API_REFUND_ENDPOINT + transaction + "/refunds", initiateRefundRequest, Collections.singletonMap("request-id", requestId.toString()), "token")).thenReturn(RefundTestFactory.defaultRefundDetailsResponseJsonObject());

        RefundDetailsResponse refundDetailsResponse = classUnderTest.postRefundRequest(initiateRefundRequest, transaction, requestId, "token");

        assertEquals(UUID.fromString("25da863a-60a5-475d-ae47-c0e4bd1bec31"), refundDetailsResponse.getRefundId());
        assertEquals(UUID.fromString("25da863a-60a5-475d-ae47-c0e4bd1bec32"), refundDetailsResponse.getRefundTransactionId());
    }

    @Test
    public void testPostRefundRequest_MinimumFields() throws Exception {
        InitiateRefundRequest initiateRefundRequest = RefundTestFactory.defaultInitiateRefundRequest();
        UUID transaction = UUID.randomUUID();
        UUID requestId = UUID.randomUUID();
        when(jsonTemplate.postWithHeader(API_REFUND_ENDPOINT + transaction + "/refunds", initiateRefundRequest, Collections.singletonMap("request-id", requestId.toString()), "token")).thenReturn(RefundTestFactory.refundDetailsResponseWithMinimumFieldsJsonObject());

        RefundDetailsResponse refundDetailsResponse = classUnderTest.postRefundRequest(initiateRefundRequest, transaction, requestId, "token");

        assertNull(refundDetailsResponse.getUpdatedAt());
        assertNull(refundDetailsResponse.getRefundTransactionId());
        assertNull(refundDetailsResponse.getDescription());
        assertNull(refundDetailsResponse.getVatCategory());
    }

    @Test
    public void testGetRefundRequest_HappyFlow() throws Exception {
        UUID transaction = UUID.randomUUID();
        UUID refund = UUID.randomUUID();
        when(jsonTemplate.get(API_REFUND_ENDPOINT + transaction + "/refunds/" + refund, "token")).thenReturn(RefundTestFactory.defaultRefundDetailsResponseJsonObject());

        RefundDetailsResponse refundDetailsResponse = classUnderTest.getRefundRequest(transaction, refund, "token");

        assertEquals(UUID.fromString("25da863a-60a5-475d-ae47-c0e4bd1bec31"), refundDetailsResponse.getRefundId());
        assertEquals(UUID.fromString("25da863a-60a5-475d-ae47-c0e4bd1bec32"), refundDetailsResponse.getRefundTransactionId());
    }

    @Test
    public void testGetRefundableDetails_HappyFlow() throws Exception {
        UUID transaction = UUID.randomUUID();
        when(jsonTemplate.get(API_REFUND_ENDPOINT + transaction + "/refundable-details", "token")).thenReturn(RefundTestFactory.defaultTransactionRefundableDetailsResponseJsonObject());

        TransactionRefundableDetailsResponse transactionRefundableDetailsResponse = classUnderTest.getRefundableDetails(transaction, "token");

        assertEquals(UUID.fromString("25da863a-60a5-475d-ae47-c0e4bd1bec31"), transactionRefundableDetailsResponse.getTransactionId());
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
        assertThat(actual.getTransactionInfo().get(0).getPaymentBrand(), is("IDEAL"));
        assertThat(actual.getTransactionInfo().get(0).getType(), is(TransactionType.PAYMENT));
        assertThat(actual.getTransactionInfo().get(0).getStatus(), is(TransactionStatus.SUCCESS));
        assertThat(actual.getTransactionInfo().get(0).getAmount().getAmount(), is(new BigDecimal("5.99")));
        assertThat(actual.getTransactionInfo().get(0).getAmount().getCurrency(), is(EUR));
        assertThat(actual.getTransactionInfo().get(0).getConfirmedAmount().isPresent(), is(true));
        assertThat(actual.getTransactionInfo().get(0).getConfirmedAmount().get().getAmount(), is(new BigDecimal("5.99")));
        assertThat(actual.getTransactionInfo().get(0).getConfirmedAmount().get().getCurrency(), is(EUR));
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
        assertThat(actual.getTransactionInfo().get(0).getPaymentBrand(), is("IDEAL"));
        assertThat(actual.getTransactionInfo().get(0).getType(), is(TransactionType.PAYMENT));
        assertThat(actual.getTransactionInfo().get(0).getStatus(), is(TransactionStatus.SUCCESS));
        assertThat(actual.getTransactionInfo().get(0).getAmount().getAmount(), is(new BigDecimal("5.99")));
        assertThat(actual.getTransactionInfo().get(0).getAmount().getCurrency(), is(EUR));
        assertThat(actual.getTransactionInfo().get(0).getConfirmedAmount().isPresent(), is(true));
        assertThat(actual.getTransactionInfo().get(0).getConfirmedAmount().get().getAmount(), is(new BigDecimal("5.99")));
        assertThat(actual.getTransactionInfo().get(0).getConfirmedAmount().get().getCurrency(), is(EUR));
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
    public void testGetOrderStatus() throws Exception {
        String orderId = "3dfe639-967a-473d-8642-fa9347223d7a";
        JSONObject orderStatus = new JSONObject().put("order", getOrderStatusResult());

        when(jsonTemplate.get("v2/orders/" + orderId, "token")).thenReturn(orderStatus);

        OrderStatusResponse actualResponse = classUnderTest.getOrderStatus(orderId, "token");
        OrderStatusResult actualResult = actualResponse.getOrderStatusResult();

        assertThat(actualResult.getMerchantOrderId(), is("25da863a-60a5-475d-ae47-c0e4bd1bec31"));
        assertThat(actualResult.getId(), is("ORDER1"));
        assertThat(actualResult.getOrderStatus(), is("COMPLETED"));
        assertThat(actualResult.getTotalAmount().getCurrency(), is(EUR));
        assertThat(actualResult.getTotalAmount().getAmount(), is(new BigDecimal("1.00")));
        assertThat(actualResult.getTransactionInfoOrderStatus().get(0).getId(), is("1"));
        assertThat(actualResult.getTransactionInfoOrderStatus().get(0).getPaymentBrand(), is("IDEAL"));
        assertThat(actualResult.getTransactionInfoOrderStatus().get(0).getType(), is(TransactionType.AUTHORIZE));
        assertThat(actualResult.getTransactionInfoOrderStatus().get(0).getStatus(), is(TransactionStatus.COMPLETED));
        assertThat(actualResult.getTransactionInfoOrderStatus().get(0).getAmount().getAmount(), is(new BigDecimal("1.00")));
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

    private JSONObject getOrderStatusResult() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("merchantOrderId", "25da863a-60a5-475d-ae47-c0e4bd1bec31");
        jsonObject.put("id", "ORDER1");
        jsonObject.put("status", "COMPLETED");
        jsonObject.put("statusLastUpdatedAt", "2000-01-01T00:00:00.000-0200");
        jsonObject.put("totalAmount", getJsonMoney(Currency.EUR, 100));

        JSONObject firstTransaction = getTransactionObject("1", 100L, TransactionType.AUTHORIZE, TransactionStatus.COMPLETED);
        JSONObject secondTransaction = getTransactionObject("2", 200L, TransactionType.CAPTURE, TransactionStatus.SUCCESS);
        JSONObject thirdTransaction = getTransactionObject("3", 300L, TransactionType.REFUND, TransactionStatus.FAILURE);
        jsonObject.put("transactions", new JSONArray(Arrays.asList(firstTransaction, secondTransaction, thirdTransaction)));
        return jsonObject;
    }

    private JSONObject getJsonMoney(Currency currency, long amountInCents) {
        JSONObject object = new JSONObject();
        object.put("valueInMinorUnits", String.valueOf(amountInCents));
        object.put("currency", currency);
        return object;
    }

    private JSONObject getTransactionObject(String id, Long amount, TransactionType transactionType, TransactionStatus transactionStatus) {
        JSONObject transactionObject = new JSONObject();
        transactionObject.put("id", id);
        transactionObject.put("paymentBrand", PaymentBrand.IDEAL);
        transactionObject.put("type", transactionType);
        transactionObject.put("status", transactionStatus);
        transactionObject.put("amount", getJsonMoney(Currency.EUR, amount));
        transactionObject.put("createdAt", "2024-07-28T12:51:15.574+02:00");
        transactionObject.put("lastUpdatedAt", "2024-07-28T12:51:15.574+02:00");
        return transactionObject;
    }
}
