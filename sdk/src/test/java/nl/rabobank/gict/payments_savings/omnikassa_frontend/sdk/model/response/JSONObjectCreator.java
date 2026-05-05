package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
}
