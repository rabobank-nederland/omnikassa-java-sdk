package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.connector.ApiConnector;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.connector.TokenProviderSpy;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions.InvalidAccessTokenException;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions.RabobankSdkException;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.AccessTokenBuilder;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.MerchantOrderTestFactory;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.Signable;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.request.MerchantOrderRequest;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.ApiNotification;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.ApiNotificationBuilder;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.IdealIssuersResponse;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.Issuer;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.IssuerLogo;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.MerchantOrderResponse;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.MerchantOrderResponseBuilder;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.MerchantOrderStatusResponse;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.MerchantOrderStatusResponseBuilder;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.PaymentBrandsResponse;
import org.apache.commons.codec.binary.Base64;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.UUID;

import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.Environment.SANDBOX;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.connector.TokenProvider.FieldName.REFRESH_TOKEN;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.JSONObjectCreator.createJSONObjectForIdealIssuer;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.JSONObjectCreator.createJSONObjectForPaymentBrands;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EndpointTest {

    private static final byte[] SIGNING_KEY = Base64.decodeBase64("c2VjcmV0");
    private static final String EXPECTED_REDIRECT_URL = "http://redirectUrl";
    private static final UUID EXPECTED_OMNIKASSA_ORDER_ID = UUID.fromString("25da863a-60a5-475d-ae47-c0e4bd1bec31");

    @Mock
    private ApiConnector apiConnector;

    private Endpoint endpoint;
    private TokenProviderSpy tokenProvider;

    @Before
    public void setUp() {
        initializeTokenProvider();
        endpoint = new Endpoint(apiConnector, tokenProvider, SIGNING_KEY);
    }

    private void initializeTokenProvider() {
        tokenProvider = new TokenProviderSpy(new HashMap<>());
        tokenProvider.setValue(REFRESH_TOKEN, "refreshToken");
    }

    @Test
    public void deprecatedAnnounceMerchantOrder_HappyFlow() throws Exception {
        tokenProvider.setValidAccessToken();

        when(apiConnector.announceMerchantOrder(any(MerchantOrderRequest.class), any(String.class))).thenReturn(prepareMerchantOrderResponse());

        String returnUrl = endpoint.announceOrder(MerchantOrderTestFactory.any());

        assertEquals(EXPECTED_REDIRECT_URL, returnUrl);
    }

    @Test
    public void announceMerchantOrder_HappyFlow() throws Exception {
        tokenProvider.setValidAccessToken();

        when(apiConnector.announceMerchantOrder(any(MerchantOrderRequest.class), any(String.class)))
                .thenReturn(prepareMerchantOrderResponse());

        MerchantOrderResponse merchantOrderResponse = endpoint.announce(MerchantOrderTestFactory.any());

        assertEquals(EXPECTED_REDIRECT_URL, merchantOrderResponse.getRedirectUrl());
        assertEquals(EXPECTED_OMNIKASSA_ORDER_ID, merchantOrderResponse.getOmnikassaOrderId());
    }

    @Test
    public void announceMerchantOrder_AccessTokenExpired() throws Exception {
        when(apiConnector.retrieveNewToken("refreshToken"))
                .thenReturn(new AccessTokenBuilder().withValidUntilTomorrow().build());
        when(apiConnector.announceMerchantOrder(any(MerchantOrderRequest.class), anyString()))
                .thenReturn(prepareMerchantOrderResponse());

        MerchantOrderResponse merchantOrderResponse = endpoint.announce(MerchantOrderTestFactory.any());

        verify(apiConnector).retrieveNewToken("refreshToken");

        assertEquals(EXPECTED_REDIRECT_URL, merchantOrderResponse.getRedirectUrl());
        assertEquals(EXPECTED_OMNIKASSA_ORDER_ID, merchantOrderResponse.getOmnikassaOrderId());
    }

    @Test
    public void announceMerchantOrder_UnexpectedAccessTokenExpired() throws Exception {
        tokenProvider.setValidAccessToken();

        when(apiConnector.announceMerchantOrder(any(MerchantOrderRequest.class), any()))
                .thenThrow(new InvalidAccessTokenException(""))
                .thenReturn(prepareMerchantOrderResponse());
        when(apiConnector.retrieveNewToken("refreshToken")).thenReturn(new AccessTokenBuilder().build());

        MerchantOrderResponse merchantOrderResponse = endpoint.announce(MerchantOrderTestFactory.any());

        // a new token is retrieved because of the exception
        verify(apiConnector).retrieveNewToken("refreshToken");

        // the actual call was performed twice
        verify(apiConnector, times(2)).announceMerchantOrder(any(MerchantOrderRequest.class), any());

        // a valid response is still returned
        assertEquals(EXPECTED_REDIRECT_URL, merchantOrderResponse.getRedirectUrl());
        assertEquals(EXPECTED_OMNIKASSA_ORDER_ID, merchantOrderResponse.getOmnikassaOrderId());
    }

    @Test(expected = InvalidAccessTokenException.class)
    public void announceMerchantOrder_UnexpectedAccessTokenExpiredTwice() throws Exception {
        when(apiConnector.announceMerchantOrder(any(MerchantOrderRequest.class), any()))
                .thenThrow(new InvalidAccessTokenException(""));
        when(apiConnector.retrieveNewToken("refreshToken")).thenReturn(new AccessTokenBuilder().build());

        endpoint.announce(MerchantOrderTestFactory.any());
    }

    @Test(expected = Test.None.class)
    public void retrieveAnnouncement_HappyFlow() throws RabobankSdkException {
        MerchantOrderStatusResponse merchantOrderStatusResponseJson = prepareMerchantOrderStatusResponseWithValidSignature();

        ApiNotification apiNotification = prepareApiNotification();
        apiNotification.setSignature(Signable.calculateSignature(apiNotification.getSignatureData(), SIGNING_KEY));

        when(apiConnector.getAnnouncementData(apiNotification)).thenReturn(merchantOrderStatusResponseJson);

        endpoint.retrieveAnnouncement(apiNotification);
    }

    @Test(expected = RabobankSdkException.class)
    public void retrieveAnnouncement_InValidSignature() throws RabobankSdkException {
        ApiNotification apiNotification = prepareApiNotification();

        endpoint.retrieveAnnouncement(apiNotification);
    }

    @Test
    public void createInstanceOldStyle() {
        Endpoint oldStyle = Endpoint.createInstance("http://localhost", new byte[]{1, 2, 3}, tokenProvider);
        assertThat(oldStyle, notNullValue());
    }

    @Test
    public void createInstanceNewStyle() {
        Endpoint newStyle = Endpoint.createInstance(SANDBOX, "c2VjcmV0", tokenProvider);
        assertThat(newStyle, notNullValue());
    }

    @Test
    public void retrievePaymentBrands() throws RabobankSdkException {
        tokenProvider.setValidAccessToken();
        PaymentBrandsResponse paymentBrandsResponse = new PaymentBrandsResponse(createJSONObjectForPaymentBrands());
        when(apiConnector.retrievePaymentBrands(any())).thenReturn(paymentBrandsResponse);

        PaymentBrandsResponse response = endpoint.retrievePaymentBrands();

        assertPaymentBrandsResponse(response);
    }

    @Test
    public void retrievePaymentBrandsRefreshesAccessToken() throws RabobankSdkException {
        PaymentBrandsResponse paymentBrandsResponse = new PaymentBrandsResponse(createJSONObjectForPaymentBrands());

        when(apiConnector.retrievePaymentBrands(any()))
                .thenThrow(new InvalidAccessTokenException(""))
                .thenReturn(paymentBrandsResponse);
        when(apiConnector.retrieveNewToken("refreshToken")).thenReturn(new AccessTokenBuilder().build());

        PaymentBrandsResponse response = endpoint.retrievePaymentBrands();

        assertPaymentBrandsResponse(response);
    }

    @Test(expected = InvalidAccessTokenException.class)
    public void retrievePaymentBrandsFailsTwiceWithInvalidAccessToken() throws RabobankSdkException {
        when(apiConnector.retrievePaymentBrands(any()))
                .thenThrow(new InvalidAccessTokenException(""));
        when(apiConnector.retrieveNewToken("refreshToken")).thenReturn(new AccessTokenBuilder().build());

        endpoint.retrievePaymentBrands();
    }

    private void assertPaymentBrandsResponse(PaymentBrandsResponse response) {
        assertThat(response, notNullValue());
        assertThat(response.getPaymentBrands(), notNullValue());
        assertThat(response.getPaymentBrands().size(), is(2));
        assertThat(response.getPaymentBrands().get(0).getName(), is("IDEAL"));
        assertThat(response.getPaymentBrands().get(0).isActive(), is(true));
        assertThat(response.getPaymentBrands().get(1).getName(), is("PAYPAL"));
        assertThat(response.getPaymentBrands().get(1).isActive(), is(false));
    }

    @Test
    public void retrieveIdealIssuers() throws RabobankSdkException {
        tokenProvider.setValidAccessToken();
        IdealIssuersResponse idealIssuersResponse = new IdealIssuersResponse(createJSONObjectForIdealIssuer());
        when(apiConnector.retrieveIdealIssuers(any())).thenReturn(idealIssuersResponse);

        IdealIssuersResponse response = endpoint.retrieveIdealIssuers();

        assertIdealIssuersResponse(response);
    }

    private void assertIdealIssuersResponse(IdealIssuersResponse response) {
        assertThat(response, notNullValue());
        assertThat(response.getIssuers(), notNullValue());
        assertThat(response.getIssuers().size(), is(1));


        Issuer issuer = response.getIssuers().get(0);
        assertThat(issuer.getId(), is("ASNBNL21"));
        assertThat(issuer.getName(), is("ASN Bank"));
        assertThat(issuer.getCountryNames(), is("Nederland"));


        assertThat(issuer.getLogos(), notNullValue());
        assertThat(issuer.getLogos().size(), is(1));
        IssuerLogo logo = issuer.getLogos().get(0);
        assertThat(logo.getUrl(), is("http://rabobank.nl/static/issuersASNBNL21.png"));
        assertThat(logo.getMimeType(), is("image/png"));
    }

    private MerchantOrderStatusResponse prepareMerchantOrderStatusResponseWithValidSignature() {
        return new MerchantOrderStatusResponseBuilder().build();
    }

    private MerchantOrderResponse prepareMerchantOrderResponse() {
        return new MerchantOrderResponseBuilder()
                .withRedirectUrl("http://redirectUrl")
                .withOmnikassaOrderId("25da863a-60a5-475d-ae47-c0e4bd1bec31")
                .build();
    }

    private ApiNotification prepareApiNotification() {
        return new ApiNotificationBuilder()
                .withSignature("b3aca76859ff5ede10543b5c116446ed79ae0d815b8d063ca40dd5dfed79f49ca57bc87fbaafb7bb2759512377ba9b177fe75c8f83614fe8b3cc46821177bdce")
                .build();
    }
}