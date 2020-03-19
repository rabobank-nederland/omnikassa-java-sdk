package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * holder object for response retrieving a list of payment brands
 */
public final class  PaymentBrandsResponse {

    private final List<PaymentBrandInfo> paymentBrands;

    public PaymentBrandsResponse(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("paymentBrands");
        paymentBrands = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            PaymentBrandInfo paymentBrandInfo = new PaymentBrandInfo(jsonArray.getJSONObject(i));
            paymentBrands.add(paymentBrandInfo);
        }
    }

    public List<PaymentBrandInfo> getPaymentBrands() {
        return paymentBrands;
    }

}
