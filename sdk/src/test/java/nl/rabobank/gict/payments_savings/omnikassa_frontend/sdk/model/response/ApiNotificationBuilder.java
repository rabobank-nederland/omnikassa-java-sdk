package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.TestBuilder;
import kong.unirest.json.JSONObject;

public class ApiNotificationBuilder implements TestBuilder<SignedResponse> {
    private int poiId = 1;
    private String authentication = "authentication";
    private String expiry = "2000-01-01T00:00:00.000-0200";
    private String eventName = "event";
    private String signature = "2ef8975ecd1425ba5f3117e797047a1bd15c0a1e4a605656a69fbf49fb767281ec6b4e24a194bcc975285ebe978cfc0b662e530ff34f5090a4abb6626376f4ff";

    public ApiNotificationBuilder withPoiId(int poiId) {
        this.poiId = poiId;
        return this;
    }

    public ApiNotificationBuilder withAuthentication(String authentication) {
        this.authentication = authentication;
        return this;
    }

    public ApiNotificationBuilder withExpiry(String expiry) {
        this.expiry = expiry;
        return this;
    }

    public ApiNotificationBuilder withEventName(String eventName) {
        this.eventName = eventName;
        return this;
    }

    public ApiNotificationBuilder withSignature(String signature) {
        this.signature = signature;
        return this;
    }

    @Override
    public ApiNotification build() {
        return new ApiNotification(poiId, authentication, expiry, eventName, signature);
    }

    @Override
    public JSONObject buildJsonObject() {
        JSONObject response = new JSONObject();
        response.put("poiId", String.valueOf(poiId));
        response.put("authentication", authentication);
        response.put("expiry", expiry);
        response.put("eventName", eventName);
        response.put("signature", signature);
        return response;
    }
}
