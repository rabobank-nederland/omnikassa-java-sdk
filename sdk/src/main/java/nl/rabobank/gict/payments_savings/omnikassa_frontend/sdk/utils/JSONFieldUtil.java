package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.utils;

import kong.unirest.json.JSONObject;

public class JSONFieldUtil {

    private JSONFieldUtil() {
        // deliberately empty
    }

    public static JSONObject getJSONObject(JSONObject jsonObject, String fieldName) {
        if (isDefined(jsonObject, fieldName)) {
            return jsonObject.getJSONObject(fieldName);
        }
        return null;
    }

    public static String getString(JSONObject jsonObject, String fieldName) {
        if (isDefined(jsonObject, fieldName)) {
            return jsonObject.getString(fieldName);
        }
        return null;
    }

    public static boolean isDefined(JSONObject jsonObject, String fieldName) {
        return jsonObject != null && jsonObject.has(fieldName) && !jsonObject.isNull(fieldName);
    }
}
