package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response;

import kong.unirest.json.*;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.TestBuilder;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.orderstatus.OrderStatusResponse;

public class OrderStatusResponseBuilder implements TestBuilder<OrderStatusResponse> {

    private String id;

    public OrderStatusResponseBuilder withId(String id) {
        this.id = id;
        return this;
    }
    @Override
    public OrderStatusResponse build() {
        return new OrderStatusResponse(buildJsonObject());
    }

    @Override
    public JSONObject buildJsonObject() {
        String builder = "{ order: {" +
                         "merchantOrderId:'ORDER123', " +
                         "id:'" + id + "', " +
                         "status:'COMPLETED', " +
                         "totalAmount: { " +
                         "currency: 'EUR', " +
                         "valueInMinorUnits: '100' }" +
                          "}" +
                         "}";
        return new JSONObject(builder);
    }
}
