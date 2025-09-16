package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.orderstatus;

import kong.unirest.json.JSONObject;

public class OrderStatusResponse {

    private final OrderStatusResult order;

    public OrderStatusResponse(JSONObject jsonObject) {
        this.order = OrderStatusResult.fromJson(jsonObject.optJSONObject("order"));
    }

    public OrderStatusResult getOrder() {
        return order;
    }
}
