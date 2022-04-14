package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.request;

import org.json.JSONObject;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.JsonConvertible;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.Money;

/**
 * class for initiating refund
 */
public class InitiateRefundRequest  implements JsonConvertible {

    private final Money money;
    private final String description;
    private final String vatCategory;


    public InitiateRefundRequest(Money money, String description, String vatCategory) {
        this.money = money;
        this.description = description;
        this.vatCategory = vatCategory;
    }

    @Override
    public JSONObject asJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("money", money.asJson());
        jsonObject.put("description", description);
        jsonObject.put("vatCategory", vatCategory);
        return jsonObject;
    }
}
