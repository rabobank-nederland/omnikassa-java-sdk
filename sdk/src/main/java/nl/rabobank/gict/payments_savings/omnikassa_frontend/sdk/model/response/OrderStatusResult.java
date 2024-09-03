package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response;

import kong.unirest.json.JSONObject;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.Money;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class OrderStatusResult {
    private final String merchantOrderId;
    private final String id;
    private final String poiId;
    private final String status;
    private final Money totalAmount;
    private final List<TransactionInfoStatus> TransactionInfoOrderStatus;

    public OrderStatusResult(JSONObject jsonObject) {
        JSONObject orderObject = jsonObject.getJSONObject("element");

        this.merchantOrderId = orderObject.getString("merchantOrderId");
        this.id = orderObject.getString("id");
        this.poiId = orderObject.getString("poiId");
        this.status = orderObject.getString("status");
        this.totalAmount = Money.fromJsonWithValueInMinorUnits(orderObject.getJSONObject("totalAmount"));
        List<TransactionInfoStatus> transactions = new ArrayList<>();
        if(orderObject.has("transactions")) {
            transactions = StreamSupport.stream(() -> orderObject.getJSONArray("transactions").spliterator(), Spliterator.ORDERED, false)
                                        .map((transactionJsonObject) -> new TransactionInfoStatus((JSONObject) transactionJsonObject))
                                        .collect(Collectors.toList());
        }
        this.TransactionInfoOrderStatus = Collections.unmodifiableList(transactions);
    }

    public String getMerchantOrderId() {
        return merchantOrderId;
    }

    public String getId() {
        return id;
    }

    public String getPointOfInteractionId() {
        return poiId;
    }

    public String getOrderStatus() {
        return status;
    }

    public Money getTotalAmount() {
        return totalAmount;
    }

    public List<TransactionInfoStatus> getTransactionInfoOrderStatus() {
        return TransactionInfoOrderStatus;
    }
}
