package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response;

import kong.unirest.json.*;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.TestBuilder;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.*;
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
        JSONObject response = new JSONObject();
        JSONObject order = new JSONObject();
        response.put("order", order);
        order.put("id", id);
        order.put("merchantOrderId", "ORDER123");
        order.put("status", "COMPLETED");

        JSONObject amount = new JSONObject();
        amount.put("currency", "EUR");
        amount.put("amount", 100);
        order.put("totalAmount", amount);

        JSONArray transactions = new JSONArray();
        JSONObject transaction = new JSONObject();
        transactions.put(transaction);

        transaction.put("id", 123);
        transaction.put("paymentBrand", "paymentBrand");
        transaction.put("type", TransactionType.AUTHORIZE);
        transaction.put("status", TransactionStatus.COMPLETED);
        transaction.put("amount", amount);
        transaction.put("createdAt", "2018-08-02T09:29:56.123+02:00");
        transaction.put("lastUpdatedAt", "2018-08-02T09:29:56+02:00");

        order.put("transactions", transactions);

        return response;
    }
}
