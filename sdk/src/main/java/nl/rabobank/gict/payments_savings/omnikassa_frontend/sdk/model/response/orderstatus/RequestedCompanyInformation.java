package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.orderstatus;

import kong.unirest.json.JSONObject;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.utils.JSONFieldUtil;

public class RequestedCompanyInformation {
    private final String name;


    public RequestedCompanyInformation(JSONObject jsonObject) {
        this.name = JSONFieldUtil.getString(jsonObject, "name");
    }

    public String getName() { return name; }
}
