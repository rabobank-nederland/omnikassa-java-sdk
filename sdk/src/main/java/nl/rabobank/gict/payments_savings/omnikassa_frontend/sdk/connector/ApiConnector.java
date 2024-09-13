package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.connector;


import kong.unirest.UnirestException;
import kong.unirest.json.JSONObject;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions.IllegalApiResponseException;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions.RabobankSdkException;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.AccessToken;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.request.InitiateRefundRequest;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.request.MerchantOrderRequest;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.ApiNotification;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.IdealIssuersResponse;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.MerchantOrderResponse;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.MerchantOrderStatusResponse;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.PaymentBrandsResponse;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.RefundDetailsResponse;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.SignedResponse;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.TransactionRefundableDetailsResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * This class manages the communication to the Rabobank api.
 * It sets up and sends JSON request to the Rabobank api.
 */
public class ApiConnector {

    public static final String X_API_USER_AGENT = "X-Api-User-Agent";

    public static final String SMARTPAY_USER_AGENT = "RabobankOmnikassaJavaSDK/1.14";
    private final byte[] signingKey;
    private final UnirestJSONTemplate jsonTemplate;

    private String userAgent;
    private String partnerReference;
    private final String suffix;

    ApiConnector(UnirestJSONTemplate jsonTemplate, String suffix, byte[] signingKey, String userAgent, String partnerReference) {
        this.jsonTemplate = jsonTemplate;
        this.signingKey = signingKey;
        this.suffix = suffix;
    }

    public ApiConnector(String baseURL, String suffix, byte[] signingKey, String userAgent, String partnerReference) {
        this(new UnirestJSONTemplate(baseURL), suffix, signingKey, userAgent, partnerReference);
    }

    /**
     * sends the MerchantOrderRequest to the Rabobank.
     *
     * @param order containing detail of the order
     * @param token access token
     * @return MerchantOrderResponse for requested order
     * @throws RabobankSdkException when problems occurred during the request, e.g. server not reachable, invalid signature, invalid authentication etc.
     */
    public MerchantOrderResponse announceMerchantOrder(final MerchantOrderRequest order, final String token)
            throws RabobankSdkException {
        return new RequestTemplate<MerchantOrderResponse>() {

            @Override
            JSONObject fetch() {
                Map<String, String> requestHeaders = new HashMap<>();
                requestHeaders.put(X_API_USER_AGENT, getUserAgentHeaderString());
                return jsonTemplate.postWithHeader(suffix + "order/server/api/v2/order", order, requestHeaders, token);
            }

            @Override
            MerchantOrderResponse convert(JSONObject result) {
                return new MerchantOrderResponse(result);
            }
        }.execute();
    }

    /**
     * retrieves the Announcement data from the Rabobank
     *
     * @param apiNotification received notification for order
     * @return MerchantOrderStatusResponse containing the information regarding the order
     * @throws RabobankSdkException when problems occurred during the request, e.g. server not reachable, invalid signature, invalid authentication etc.
     */
    public MerchantOrderStatusResponse getAnnouncementData(final ApiNotification apiNotification)
            throws RabobankSdkException {
        return new SignedRequestTemplate<MerchantOrderStatusResponse>() {

            @Override
            JSONObject fetch() {
                return jsonTemplate.get(suffix + "order/server/api/v2/events/results/" + apiNotification.getEventName(),
                                        apiNotification.getAuthentication());
            }

            @Override
            MerchantOrderStatusResponse convert(JSONObject result) {
                return new MerchantOrderStatusResponse(result);
            }
        }.execute();
    }

    /**
     * sends the InitiateRefundRequest to the Rabobank.
     *
     * @param refundRequest containing detail of the refund
     * @param transactionId id of transaction
     * @param requestId     id of request
     * @param token         access token
     * @return RefundDetailsResponse for requested refund
     * @throws RabobankSdkException when problems occurred during the request, e.g. server not reachable, invalid signature, invalid authentication etc.
     */
    public RefundDetailsResponse postRefundRequest(final InitiateRefundRequest refundRequest,
                                                   final UUID transactionId,
                                                   final UUID requestId,
                                                   final String token)
            throws RabobankSdkException {
        return new RequestTemplate<RefundDetailsResponse>() {

            @Override
            JSONObject fetch() throws UnirestException {
                Map<String, String> requestHeaders = new HashMap<>();
                requestHeaders.put("request-id", requestId.toString());
                return jsonTemplate.postWithHeader(suffix + "order/server/api/v2/refund/transactions/" + transactionId + "/refunds", refundRequest, requestHeaders, token);
            }

            @Override
            RefundDetailsResponse convert(JSONObject result) {
                return new RefundDetailsResponse(result);
            }
        }.execute();
    }

    /**
     * retrieves the RefundDetailsResponse from the Rabobank.
     *
     * @param refundId      id of the refund
     * @param transactionId id of transaction
     * @param token         access token
     * @return RefundDetailsResponse for requested refund
     * @throws RabobankSdkException when problems occurred during the request, e.g. server not reachable, invalid signature, invalid authentication etc.
     */
    public RefundDetailsResponse getRefundRequest(final UUID transactionId, final UUID refundId, final String token)
            throws RabobankSdkException {
        return new RequestTemplate<RefundDetailsResponse>() {

            @Override
            JSONObject fetch() throws UnirestException {
                return jsonTemplate.get(suffix + "order/server/api/v2/refund/transactions/" + transactionId + "/refunds/" + refundId, token);
            }

            @Override
            RefundDetailsResponse convert(JSONObject result) {
                return new RefundDetailsResponse(result);
            }
        }.execute();
    }

    /**
     * retrieves the TransactionRefundableDetailsResponse from the Rabobank.
     *
     * @param transactionId id of transaction
     * @param token         access token
     * @return TransactionRefundableDetailsResponse for initiated refund
     * @throws RabobankSdkException when problems occurred during the request, e.g. server not reachable, invalid signature, invalid authentication etc.
     */
    public TransactionRefundableDetailsResponse getRefundableDetails(final UUID transactionId, final String token)
            throws RabobankSdkException {
        return new RequestTemplate<TransactionRefundableDetailsResponse>() {

            @Override
            JSONObject fetch() throws UnirestException {
                return jsonTemplate.get(suffix + "order/server/api/v2/refund/transactions/" + transactionId + "/refundable-details", token);
            }

            @Override
            TransactionRefundableDetailsResponse convert(JSONObject result) {
                return new TransactionRefundableDetailsResponse(result);
            }
        }.execute();
    }

    public AccessToken retrieveNewToken(final String refreshToken) throws RabobankSdkException {
        return new RequestTemplate<AccessToken>() {

            @Override
            JSONObject fetch() {
                return jsonTemplate.get(suffix + "gatekeeper/refresh", refreshToken);
            }

            @Override
            AccessToken convert(JSONObject result) {
                return new AccessToken(result);
            }
        }.execute();
    }

    public PaymentBrandsResponse retrievePaymentBrands(final String accessToken) throws RabobankSdkException {
        return new RequestTemplate<PaymentBrandsResponse>() {

            @Override
            JSONObject fetch() {
                return jsonTemplate.get(suffix + "order/server/api/payment-brands", accessToken);
            }

            @Override
            PaymentBrandsResponse convert(JSONObject result) {
                return new PaymentBrandsResponse(result);
            }
        }.execute();
    }

    public IdealIssuersResponse retrieveIdealIssuers(final String accessToken) throws RabobankSdkException {
        return new RequestTemplate<IdealIssuersResponse>() {
            @Override
            JSONObject fetch() {
                return jsonTemplate.get(suffix + "ideal/server/api/v2/issuers", accessToken);
            }

            @Override
            IdealIssuersResponse convert(JSONObject result) {
                return new IdealIssuersResponse(result);
            }
        }.execute();
    }

    private abstract static class RequestTemplate<T> {
        final T execute() throws RabobankSdkException {
            return fetchValidateAndConvert();
        }

        T fetchValidateAndConvert() throws RabobankSdkException {
            try {
                JSONObject jsonResponse = fetch();

                checkedForErrorsInResponse(jsonResponse);

                return convert(jsonResponse);

            } catch (UnirestException e) {
                throw new RabobankSdkException(e);
            }
        }

        private void checkedForErrorsInResponse(JSONObject jsonObject) throws IllegalApiResponseException {
            if (jsonObject.has(IllegalApiResponseException.ERROR_CODE_FIELD_NAME)) {
                throw IllegalApiResponseException.of(jsonObject);
            }
        }

        abstract JSONObject fetch();

        abstract T convert(JSONObject result);
    }

    private abstract class SignedRequestTemplate<T extends SignedResponse> extends RequestTemplate<T> {
        @Override
        final T fetchValidateAndConvert() throws RabobankSdkException {
            T converted = super.fetchValidateAndConvert();

            converted.validateSignature(signingKey);
            return converted;
        }
    }

    private String getUserAgentHeaderString() {
        String userAgentHeader = SMARTPAY_USER_AGENT;
        if (userAgent != null) {
            userAgentHeader += " " + userAgent;
        }
        if (this.partnerReference != null) {
            userAgentHeader += " (pr: " + partnerReference + ")";
        }
        return userAgentHeader;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public void setPartnerReference(String partnerReference) {
        this.partnerReference = partnerReference;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getPartnerReference() {
        return partnerReference;
    }
}
