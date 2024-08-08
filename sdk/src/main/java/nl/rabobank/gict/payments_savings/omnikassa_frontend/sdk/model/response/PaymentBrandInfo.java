package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response;

import kong.unirest.json.JSONObject;

/**
 * result object for retrieving payment brand object
 */
public final class PaymentBrandInfo {

    private final String name;
    private final boolean active;

    PaymentBrandInfo(String name, boolean active) {
        this.name = name;
        this.active = active;
    }

    PaymentBrandInfo(JSONObject jsonObject) {
        this(jsonObject.getString("name"), jsonObject.getString("status").equalsIgnoreCase("ACTIVE"));
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }
}
