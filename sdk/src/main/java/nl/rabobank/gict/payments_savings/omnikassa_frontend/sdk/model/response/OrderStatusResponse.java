package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response;

import kong.unirest.json.JSONObject;

public class OrderStatusResponse {

    private final OrderStatusResult orderStatusResult;

    public OrderStatusResponse(JSONObject jsonObject) {
        this.orderStatusResult = new OrderStatusResult(jsonObject);
    }

    public OrderStatusResult getOrderStatusResult() {
        return orderStatusResult;
    }
}
