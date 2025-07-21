package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.TestBuilder;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.TokenStatus;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.cardonfile.ShopperPaymentDetailsResponse;

public class ShopperPaymentDetailsResponseBuilder implements TestBuilder<ShopperPaymentDetailsResponse>{
    private String id;

    public ShopperPaymentDetailsResponseBuilder withId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public ShopperPaymentDetailsResponse build() {
        return new ShopperPaymentDetailsResponse(buildJsonObject());
    }

    @Override
    public JSONObject buildJsonObject() {
        JSONObject response = new JSONObject();

        JSONArray cardOnFileList = new JSONArray();
        JSONObject cardOnFile = new JSONObject();
        cardOnFileList.put(cardOnFile);

        cardOnFile.put("id", id);
        cardOnFile.put("last4Digits", "0127");
        cardOnFile.put("brand", "MAESTRO");
        cardOnFile.put("cardExpiry", "4298-40");
        cardOnFile.put("tokenExpiry", "1607-22");
        cardOnFile.put("status", TokenStatus.ACTIVE);

        response.put("cardOnFileList", cardOnFileList);

        return response;
    }
}
