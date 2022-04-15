package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.connector;

import com.mashape.unirest.http.exceptions.UnirestException;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.request.InitiateRefundRequest;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.IdealIssuersResponse;
import org.json.JSONObject;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions.IllegalApiResponseException;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions.RabobankSdkException;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.AccessToken;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.request.MerchantOrderRequest;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.ApiNotification;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.MerchantOrderResponse;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.MerchantOrderStatusResponse;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.PaymentBrandsResponse;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.RefundDetailsResponse;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.SignedResponse;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.TransactionRefundableDetailsResponse;

import java.util.UUID;

/**
 * This class manages the communication to the Rabobank api.
 * It sets up and sends JSON request to the Rabobank api.
 */
public class ApiConnector {
    private final byte[] signingKey;
    private final UnirestJSONTemplate jsonTemplate;

    ApiConnector(UnirestJSONTemplate jsonTemplate, byte[] signingKey) {
        this.jsonTemplate = jsonTemplate;
        this.signingKey = signingKey;
    }

    public ApiConnector(String baseURL, byte[] signingKey) {
        this(new UnirestJSONTemplate(baseURL), signingKey);
    }

    /**
     * sends the MerchantOrderRequest to the Rabobank.
     * @param order containing detail of the order
     * @param token access token
     * @return MerchantOrderResponse for requested order
     * @throws RabobankSdkException when problems occurred during the request, e.g. server not reachable, invalid signature, invalid authentication etc.
     */
    public MerchantOrderResponse announceMerchantOrder(final MerchantOrderRequest order, final String token)
            throws RabobankSdkException {
        return new RequestTemplate<MerchantOrderResponse>() {

            @Override
            JSONObject fetch() throws UnirestException {
                return jsonTemplate.post("order/server/api/v2/order", order, token);
            }

            @Override
            MerchantOrderResponse convert(JSONObject result) {
                return new MerchantOrderResponse(result);
            }
        }.execute();
    }

    /**
     * retrieves the Announcement data from the Rabobank
     * @param apiNotification received notification for order
     * @return MerchantOrderStatusResponse containing the information regarding the order
     * @throws RabobankSdkException when problems occurred during the request, e.g. server not reachable, invalid signature, invalid authentication etc.
     */
    public MerchantOrderStatusResponse getAnnouncementData(final ApiNotification apiNotification)
            throws RabobankSdkException {
        return new SignedRequestTemplate<MerchantOrderStatusResponse>() {

            @Override
            JSONObject fetch() throws UnirestException {
                return jsonTemplate.get("order/server/api/v2/events/results/" + apiNotification.getEventName(),
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
     * @param refundRequest containing detail of the refund
     * @param transactionId id of transaction
     * @param requestId id of request
     * @param token access token
     * @return RefundDetailsResponse for requested refund
     * @throws RabobankSdkException when problems occurred during the request, e.g. server not reachable, invalid signature, invalid authentication etc.
     */
    public RefundDetailsResponse postRefundRequest(final InitiateRefundRequest refundRequest, final UUID transactionId, final UUID requestId, final String token)
            throws RabobankSdkException {
        return new RequestTemplate<RefundDetailsResponse>() {

            @Override
            JSONObject fetch() throws UnirestException {
                return jsonTemplate.postWithHeader("order/server/api/v2/refund/transactions/"+ transactionId +"/refunds", refundRequest,"request-id", requestId.toString(), token);
            }

            @Override
            RefundDetailsResponse convert(JSONObject result) {
                return new RefundDetailsResponse(result);
            }
        }.execute();
    }

    /**
     * retrieves the RefundDetailsResponse from the Rabobank.
     * @param refundId id of the refund
     * @param transactionId id of transaction
     * @param token access token
     * @return RefundDetailsResponse for requested refund
     * @throws RabobankSdkException when problems occurred during the request, e.g. server not reachable, invalid signature, invalid authentication etc.
     */
    public RefundDetailsResponse getRefundRequest(final UUID transactionId, final UUID refundId, final String token)
            throws RabobankSdkException {
        return new RequestTemplate<RefundDetailsResponse>() {

            @Override
            JSONObject fetch() throws UnirestException {
                return jsonTemplate.get("order/server/api/v2/refund/transactions/"+ transactionId +"/refunds/"+ refundId, token);
            }

            @Override
            RefundDetailsResponse convert(JSONObject result) {
                return new RefundDetailsResponse(result);
            }
        }.execute();
    }

    /**
     * retrieves the TransactionRefundableDetailsResponse from the Rabobank.
     * @param transactionId id of transaction
     * @param token access token
     * @return TransactionRefundableDetailsResponse for initiated refund
     * @throws RabobankSdkException when problems occurred during the request, e.g. server not reachable, invalid signature, invalid authentication etc.
     */
    public TransactionRefundableDetailsResponse getRefundableDetails(final UUID transactionId, final String token)
            throws RabobankSdkException {
        return new RequestTemplate<TransactionRefundableDetailsResponse>() {

            @Override
            JSONObject fetch() throws UnirestException {
                return jsonTemplate.get("order/server/api/v2/refund/transactions/"+ transactionId +"/refundable-details", token);
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
            JSONObject fetch() throws UnirestException {
                return jsonTemplate.get("gatekeeper/refresh", refreshToken);
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
            JSONObject fetch() throws UnirestException {
                return jsonTemplate.get("order/server/api/payment-brands", accessToken);
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
            JSONObject fetch() throws  UnirestException {
                return jsonTemplate.get("ideal/server/api/v2/issuers", accessToken);
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

        abstract JSONObject fetch() throws UnirestException;

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
}