package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * This response contains the information regarding an order
 */
public final class MerchantOrderStatusResponse extends SignedResponse {

    private final boolean moreOrderResultsAvailable;
    private final List<MerchantOrderResult> orderResults;

    public MerchantOrderStatusResponse(JSONObject jsonObject) {
        super(jsonObject);

        JSONArray jsonArray = jsonObject.getJSONArray("orderResults");
        orderResults = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            MerchantOrderResult orderResult = new MerchantOrderResult(jsonArray.getJSONObject(i));
            orderResults.add(orderResult);
        }
        this.moreOrderResultsAvailable = jsonObject.getBoolean("moreOrderResultsAvailable");
    }

    public boolean moreOrderResultsAvailable() {
        return moreOrderResultsAvailable;
    }

    public List<MerchantOrderResult> getOrderResults() {
        return orderResults;
    }

    @Override
    public List<String> getSignatureData() {
        List<String> data = new ArrayList<>();
        data.add(String.valueOf(moreOrderResultsAvailable));
        for (MerchantOrderResult result : orderResults) {
            data.addAll(result.getSignatureData());
        }
        return data;
    }
}
