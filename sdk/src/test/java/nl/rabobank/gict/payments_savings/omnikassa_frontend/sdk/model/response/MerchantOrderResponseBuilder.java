package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.TestBuilder;
import kong.unirest.json.JSONObject;

public final class MerchantOrderResponseBuilder implements TestBuilder<MerchantOrderResponse> {
    private String redirectUrl = "http://returnAdress";
    private String omnikassaOrderId;

    public MerchantOrderResponseBuilder withRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
        return this;
    }

    public MerchantOrderResponseBuilder withOmnikassaOrderId(String omnikassaOrderId) {
        this.omnikassaOrderId = omnikassaOrderId;
        return this;
    }

    @Override
    public MerchantOrderResponse build() {
        return new MerchantOrderResponse(buildJsonObject());
    }

    @Override
    public JSONObject buildJsonObject() {
        JSONObject response = new JSONObject();
        response.put("redirectUrl", redirectUrl);
        response.put("omnikassaOrderId", omnikassaOrderId);
        return response;
    }
}
