package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.IdealIssuersResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.IdealIssuersResponse.ISSUERS_KEY;

public final class JSONObjectCreator {

    public static JSONObject createJSONObjectForPaymentBrands() {
        JSONArray paymentBrands = new JSONArray();
        paymentBrands.put(createPaymentBrand("IDEAL", "Active"));
        paymentBrands.put(createPaymentBrand("PAYPAL", "Inactive"));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("paymentBrands", paymentBrands);

        return jsonObject;
    }

    private static Map<String, String> createPaymentBrand(String name, String status) {
        Map<String, String> paymentBrand = new HashMap<>();
        paymentBrand.put("name", name);
        paymentBrand.put("status", status);
        return paymentBrand;
    }

    public static JSONObject createJSONObjectForIdealIssuer() {
        JSONObject jsonIssuersResponse = new JSONObject();
        JSONArray issuers = new JSONArray();
        issuers.put(createJSONObjectForIssuer());
        jsonIssuersResponse.put(ISSUERS_KEY, issuers);

        return  jsonIssuersResponse;
    }

    private static JSONObject createJSONObjectForIssuer() {
        JSONObject issuer = new JSONObject();

        JSONArray jsonLogosArray = new JSONArray();
        JSONObject jsonLogo = new JSONObject();
        jsonLogo.put("url", "http://rabobank.nl/static/issuersASNBNL21.png")
                .put("mimeType", "image/png");
        jsonLogosArray.put(jsonLogo);

        issuer.put("id", "ASNBNL21")
                .put("name", "ASN Bank")
                .put("countryNames", "Nederland")
                .put("logos", jsonLogosArray);

        return issuer;
    }
}
