package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.cardonfile.CardsOnFileResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.connector.ApiConnector;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.connector.TokenProvider;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions.InvalidAccessTokenException;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions.RabobankSdkException;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.AccessToken;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.MerchantOrder;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.request.InitiateRefundRequest;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.request.MerchantOrderRequest;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.ApiNotification;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.IdealIssuersResponse;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.MerchantOrderResponse;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.MerchantOrderStatusResponse;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.PaymentBrandsResponse;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.RefundDetailsResponse;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.TransactionRefundableDetailsResponse;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.orderstatus.*;

import java.util.UUID;

import static org.apache.commons.codec.binary.Base64.decodeBase64;

/**
 * this class is the entry point for this SDK. It exposes the functionality that third parties can use to connect with ROFE.
 * It validates and refreshes the AccessToken.
 */
public final class Endpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(Endpoint.class);

    private final ApiConnector connector;
    private final TokenProvider tokenProvider;
    private final byte[] signingKey;

    Endpoint(ApiConnector connector, TokenProvider tokenProvider, byte[] signingKey) {
        this.connector = connector;
        this.tokenProvider = tokenProvider;
        this.signingKey = signingKey;
    }

    /**
     * Use this method to retrieve an instance of the Endpont
     *
     * @param baseURL       this is the Url that points to the Rabobank API
     * @param signingKey    this is the key given by the Rabobank to sign all communication
     * @param tokenProvider this must be your own implementation of the tokenProvider, see developer-manual
     * @return new instance of Endpoint
     * Note: for backward compatibility do not remove this method!
     * @deprecated This method is deprecated, use the {@link Endpoint#createInstance(Environment, String, TokenProvider)} instead.
     */
    @Deprecated
    public static Endpoint createInstance(String baseURL, String suffix, byte[] signingKey, TokenProvider tokenProvider) {
        return createInstance(baseURL, suffix, signingKey, tokenProvider, null, null);
    }

    /**
     * Use this method to retrieve an instance of the Endpont
     *
     * @param baseURL          this is the Url that points to the Rabobank API
     * @param signingKey       this is the key given by the Rabobank to sign all communication
     * @param tokenProvider    this must be your own implementation of the tokenProvider, see developer-manual
     * @param userAgent        this is the User-Agent value you want to give your implementation
     * @param partnerReference this can be filled with the partner reference, if applicable.
     * @return new instance of Endpoint
     * Note: for backward compatibility do not remove this method!
     * @deprecated This method is deprecated, use the {@link Endpoint#createInstance(Environment, String, TokenProvider)} instead.
     */
    @Deprecated
    public static Endpoint createInstance(String baseURL,
                                          String suffix,
                                          byte[] signingKey,
                                          TokenProvider tokenProvider,
                                          String userAgent,
                                          String partnerReference) {
        ApiConnector connector = new ApiConnector(baseURL, suffix, signingKey, userAgent, partnerReference);
        return new Endpoint(connector, tokenProvider, signingKey);
    }

    /**
     * Use this method to retrieve an instance of the Endpont
     *
     * @param environment             this is the Url that points to the Rabobank API
     * @param base64encodedSigningKey this is the key given by the Rabobank to sign all communication
     * @param tokenProvider           this must be your own implementation of the tokenProvider, see developer-manual
     * @return new instance of Endpoint
     */
    public static Endpoint createInstance(Environment environment,
                                          String base64encodedSigningKey,
                                          TokenProvider tokenProvider) {
        return createInstance(environment, base64encodedSigningKey, tokenProvider, null);
    }

    /**
     * Use this method to retrieve an instance of the Endpont
     *
     * @param environment             this is the Url that points to the Rabobank API
     * @param base64encodedSigningKey this is the key given by the Rabobank to sign all communication
     * @param tokenProvider           this must be your own implementation of the tokenProvider, see developer-manual
     * @param userAgent               this is the User-Agent value you want to give your implementation
     * @return new instance of Endpoint
     */
    public static Endpoint createInstance(Environment environment,
                                          String base64encodedSigningKey,
                                          TokenProvider tokenProvider,
                                          String userAgent) {
        return createInstance(environment, base64encodedSigningKey, tokenProvider, userAgent, null);
    }

    /**
     * Use this method to retrieve an instance of the Endpont
     *
     * @param environment             this is the Url that points to the Rabobank API
     * @param base64encodedSigningKey this is the key given by the Rabobank to sign all communication
     * @param tokenProvider           this must be your own implementation of the tokenProvider, see developer-manual
     * @param userAgent               this is the User-Agent value you want to give your implementation
     * @param partnerReference        this can be filled with the partner reference, if applicable.
     * @return new instance of Endpoint
     */
    public static Endpoint createInstance(Environment environment,
                                          String base64encodedSigningKey,
                                          TokenProvider tokenProvider,
                                          String userAgent,
                                          String partnerReference) {
        byte[] signingKey = decodeBase64(base64encodedSigningKey);
        return createInstance(environment.getUrl(), environment.getSuffix(), signingKey, tokenProvider, userAgent, partnerReference);
    }

    /**
     * This function will send the MerchantOrder to the Rabobank.
     *
     * @param merchantOrder containing the details of the order
     * @return The result is the issuer selection Url to which the customer should be redirected.
     * @throws RabobankSdkException when problems occurred during the request, e.g. server not reachable, invalid signature, invalid authentication etc.
     */
    public String announceOrder(MerchantOrder merchantOrder) throws RabobankSdkException {
        return announce(merchantOrder).getRedirectUrl();
    }


    private MerchantOrderResponse doAnnounceOrder(MerchantOrderRequest merchantOrderRequest)
            throws RabobankSdkException {
        return connector.announceMerchantOrder(merchantOrderRequest, tokenProvider.getAccessToken());
    }

    /**
     * this function send the MerchantOrder to Rabobank
     *
     * @param merchantOrder containing detail of the order
     * @return holder object contains redirect url and omnikassa order id
     * @throws RabobankSdkException when problems occurred during the request, e.g. server not reachable, invalid signature, invalid authentication etc.
     */
    public MerchantOrderResponse announce(MerchantOrder merchantOrder) throws RabobankSdkException {
        validateAccessToken();

        MerchantOrderRequest merchantOrderRequest = new MerchantOrderRequest(merchantOrder);

        try {
            return doAnnounceOrder(merchantOrderRequest);
        } catch (InvalidAccessTokenException e) {
            logAndGetNewToken(e);
            return doAnnounceOrder(merchantOrderRequest);
        }
    }

    /**
     * This function will retrieve the announcement details, these can be used to check the status of your order.
     *
     * @param apiNotification The notification that was received via the webhook, the object can be constructed via Jackson or with the direct constructor
     * @return The response contains order details like: 'status', 'paid amount' and 'currency', which can be used to update the order with the latest status.
     * @throws RabobankSdkException when problems occurred during the request, e.g. server not reachable, invalid signature, invalid authentication etc.
     */
    public MerchantOrderStatusResponse retrieveAnnouncement(ApiNotification apiNotification)
            throws RabobankSdkException {
        apiNotification.validateSignature(signingKey);

        return connector.getAnnouncementData(apiNotification);
    }

    /**
     * This function will initiate refund transaction.
     *
     * @param refundRequest The request for refund, the object can be constructed via Jackson or with the direct constructor
     * @param transactionId The transactionId of transaction for which the refund request is sent
     * @param requestId     The requestId, unique id of refund request
     * @return The response contains refund details, which can be used to update the refund with the latest status.
     * @throws RabobankSdkException when problems occurred during the request, e.g. server not reachable, invalid signature, invalid authentication etc.
     */
    public RefundDetailsResponse initiateRefundTransaction(InitiateRefundRequest refundRequest,
                                                           UUID transactionId,
                                                           UUID requestId)
            throws RabobankSdkException {
        try {
            return connector.postRefundRequest(refundRequest, transactionId, requestId, tokenProvider.getAccessToken());
        } catch (InvalidAccessTokenException e) {
            logAndGetNewToken(e);
            return connector.postRefundRequest(refundRequest, transactionId, requestId, tokenProvider.getAccessToken());
        }
    }

    /**
     * This function will get refund details.
     *
     * @param refundId      The id of initiated refund request
     * @param transactionId The transactionId of transaction for which the refund request is sent
     * @return The response contains refund details, which can be used to update the refund with the latest status.
     * @throws RabobankSdkException when problems occurred during the request, e.g. server not reachable, invalid signature, invalid authentication etc.
     */
    public RefundDetailsResponse fetchRefundTransaction(UUID transactionId, UUID refundId)
            throws RabobankSdkException {
        try {
            return connector.getRefundRequest(transactionId, refundId, tokenProvider.getAccessToken());
        } catch (InvalidAccessTokenException e) {
            logAndGetNewToken(e);
            return connector.getRefundRequest(transactionId, refundId, tokenProvider.getAccessToken());
        }
    }

    /**
     * This function will get details for specific refund within transaction.
     *
     * @param transactionId The transactionId of transaction for which the refund request is sent
     * @return The response contains refund details, which can be used to update the refund with the latest status.
     * @throws RabobankSdkException when problems occurred during the request, e.g. server not reachable, invalid signature, invalid authentication etc.
     */
    public TransactionRefundableDetailsResponse fetchRefundableTransactionDetails(UUID transactionId)
            throws RabobankSdkException {
        try {
            return connector.getRefundableDetails(transactionId, tokenProvider.getAccessToken());
        } catch (InvalidAccessTokenException e) {
            logAndGetNewToken(e);
            return connector.getRefundableDetails(transactionId, tokenProvider.getAccessToken());
        }
    }

    /**
     *
     *  This function will get details for specific order.
     *
     * @param orderId id of Order
     * @return String for order status
     * @throws RabobankSdkException when problems occurred during the request, e.g. server not reachable, invalid signature, invalid authentication etc.
     */

    public OrderStatusResponse getOrderStatus(String orderId)
            throws RabobankSdkException {

            return connector.getOrderStatus(orderId, tokenProvider.getAccessToken());
    }

    /**
     *
     *  This function will get list of cards on file for specific shopper.
     *
     * @param shopperRef reference of Shopper
     * @return The response contains list of cards stored by shopper.
     * @throws RabobankSdkException when problems occurred during the request, e.g. server not reachable, invalid signature, invalid authentication etc.
     */

    public CardsOnFileResponse getShopperPaymentDetails(String shopperRef)
            throws RabobankSdkException {
        validateAccessToken();
        return connector.getShopperPaymentDetails(shopperRef, tokenProvider.getAccessToken());
    }

    /**
     *
     *  This function will delete specific card details from list of cards on file for specific shopper.
     *
     * @param shopperRef reference of Shopper
     * @param reference reference of Card
     * @return HTTP status code
     * @throws RabobankSdkException when problems occurred during the request, e.g. server not reachable, invalid signature, invalid authentication etc.
     */

    public int deleteShopperPaymentDetails(String shopperRef, String reference) throws RabobankSdkException{

        return connector.deleteShopperPaymentDetails(shopperRef, reference, tokenProvider.getAccessToken());
    }


    public PaymentBrandsResponse retrievePaymentBrands() throws RabobankSdkException {
        validateAccessToken();
        try {
            return connector.retrievePaymentBrands(tokenProvider.getAccessToken());
        } catch (InvalidAccessTokenException e) {
            logAndGetNewToken(e);
            return connector.retrievePaymentBrands(tokenProvider.getAccessToken());
        }
    }

    public IdealIssuersResponse retrieveIdealIssuers() throws RabobankSdkException {
        validateAccessToken();
        try {
            return connector.retrieveIdealIssuers(tokenProvider.getAccessToken());
        } catch (InvalidAccessTokenException e) {
            logAndGetNewToken(e);
            return connector.retrieveIdealIssuers(tokenProvider.getAccessToken());
        }
    }

    private void logAndGetNewToken(InvalidAccessTokenException e) throws RabobankSdkException {
        // we might have mistakenly assumed the token was still valid
        LOGGER.warn("The token which we assumed to be valid was not accepted, forcing a new access token and retrying", e);
        retrieveNewToken();
    }

    private void validateAccessToken() throws RabobankSdkException {
        if (tokenProvider.hasNoValidAccessToken()) {
            retrieveNewToken();
        }
    }

    private void retrieveNewToken() throws RabobankSdkException {
        LOGGER.debug("Trying to retrieve new AccessToken from the Rabobank");
        AccessToken retrievedToken = connector.retrieveNewToken(tokenProvider.getRefreshToken());
        tokenProvider.setAccessToken(retrievedToken);
    }
}
