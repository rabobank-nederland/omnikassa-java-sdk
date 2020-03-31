package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model;

import org.json.JSONObject;

public interface JsonConvertible {
    /**
     * returns this object in JSON format
     *
     * @return JSONObject
     */
    JSONObject asJson();
}
