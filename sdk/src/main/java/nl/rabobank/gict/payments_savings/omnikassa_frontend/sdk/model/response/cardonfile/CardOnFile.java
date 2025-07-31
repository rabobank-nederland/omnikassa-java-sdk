package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.cardonfile;

import kong.unirest.json.JSONObject;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.TokenStatus;

public class CardOnFile {
    private final String id;
    private final String last4Digits;
    private final String brand;
    private final String cardExpiry;
    private final String tokenExpiry;
    private final TokenStatus status;

    public CardOnFile(JSONObject jsonObject) {
        this.id = jsonObject.getString("id");
        this.last4Digits = jsonObject.getString("last4Digits");
        this.brand = jsonObject.getString("brand");
        this.cardExpiry = jsonObject.getString("cardExpiry");
        this.tokenExpiry = jsonObject.getString("tokenExpiry");
        this.status = jsonObject.getEnum(TokenStatus.class, "status");
    }

    public String getId() { return id; }

    public String getLast4Digits() { return last4Digits; }

    public String getBrand() { return brand; }

    public String getCardExpiry() { return cardExpiry; }

    public String getTokenExpiry() { return tokenExpiry;}

    public TokenStatus getStatus() { return status; }
}
