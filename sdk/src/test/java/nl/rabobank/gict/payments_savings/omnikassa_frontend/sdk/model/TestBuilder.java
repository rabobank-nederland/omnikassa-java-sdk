package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model;

import org.json.JSONObject;

public interface TestBuilder<T> {
    T build();

    JSONObject buildJsonObject();
}
