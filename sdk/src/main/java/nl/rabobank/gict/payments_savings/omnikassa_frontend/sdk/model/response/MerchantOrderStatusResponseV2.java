package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * This response contains the information regarding an order
 */
public final class MerchantOrderStatusResponseV2 extends SignedResponse {

    private final boolean moreOrderResultsAvailable;
    private final List<MerchantOrderResultV2> orderResults;

    public MerchantOrderStatusResponseV2(JSONObject jsonObject) {
        super(jsonObject);

        JSONArray jsonArray = jsonObject.getJSONArray("orderResults");
        orderResults = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            MerchantOrderResultV2 orderResult = new MerchantOrderResultV2(jsonArray.getJSONObject(i));
            orderResults.add(orderResult);
        }
        this.moreOrderResultsAvailable = jsonObject.getBoolean("moreOrderResultsAvailable");
    }

    public boolean moreOrderResultsAvailable() {
        return moreOrderResultsAvailable;
    }

    public List<MerchantOrderResultV2> getOrderResults() {
        return orderResults;
    }

    @Override
    public List<String> getSignatureData() {
        List<String> data = new ArrayList<>();
        data.add(String.valueOf(moreOrderResultsAvailable));
        for (MerchantOrderResultV2 result : orderResults) {
            data.addAll(result.getSignatureData());
        }
        return data;
    }
}
