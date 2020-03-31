package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response;

import org.json.JSONObject;

import java.util.UUID;

/**
 * Response from the Rabobank API when an order is announced, contains the redirectUrl which points to the payment page
 * and omnikassa order id of the order
 */
public final class MerchantOrderResponse {
    private final String redirectUrl;
    private final UUID omnikassaOrderId;

    public MerchantOrderResponse(JSONObject jsonObject) {
        this.redirectUrl = jsonObject.getString("redirectUrl");
        this.omnikassaOrderId = UUID.fromString(jsonObject.getString("omnikassaOrderId"));
    }

    /**
     * @return url to which the user should be redirected
     */
    public String getRedirectUrl() {
        return redirectUrl;
    }

    /**
     * @return omnikassa order id where order is announced
     */
    public UUID getOmnikassaOrderId() {
        return omnikassaOrderId;
    }
}
