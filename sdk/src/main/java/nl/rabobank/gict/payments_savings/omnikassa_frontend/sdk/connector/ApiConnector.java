package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.connector;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions.IllegalApiResponseException;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions.RabobankSdkException;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.AccessToken;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.request.MerchantOrderRequest;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.ApiNotification;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.MerchantOrderResponse;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.MerchantOrderStatusResponse;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.PaymentBrandsResponse;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.SignedResponse;

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
     */
    public MerchantOrderStatusResponse getAnnouncementData(final ApiNotification apiNotification)
            throws RabobankSdkException {
        return new SignedRequestTemplate<MerchantOrderStatusResponse>() {

            @Override
            JSONObject fetch() throws UnirestException {
                return jsonTemplate.get("order/server/api/events/results/" + apiNotification.getEventName(),
                                        apiNotification.getAuthentication());
            }

            @Override
            MerchantOrderStatusResponse convert(JSONObject result) {
                return new MerchantOrderStatusResponse(result);
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