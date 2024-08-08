package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model;

import kong.unirest.json.JSONObject;

public interface TestBuilder<T> {
    T build();

    JSONObject buildJsonObject();
}
