package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.orderstatus;

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
    private final String status;
    private final Money totalAmount;
    private final List<TransactionStatusInfo> transactions;

    public OrderStatusResult(JSONObject jsonObject) {
        this.merchantOrderId = jsonObject.getString("merchantOrderId");
        this.id = jsonObject.getString("id");
        this.status = jsonObject.getString("status");
        this.totalAmount = Money.fromJson(jsonObject.getJSONObject("totalAmount"));
        List<TransactionStatusInfo> transactions = new ArrayList<>();
        if(jsonObject.has("transactions")) {
            transactions = StreamSupport.stream(() -> jsonObject.getJSONArray("transactions").spliterator(), Spliterator.ORDERED, false)
                                        .map((transactionJsonObject) -> new TransactionStatusInfo((JSONObject) transactionJsonObject))
                                        .collect(Collectors.toList());
        }
        this.transactions = Collections.unmodifiableList(transactions);
    }

    public String getMerchantOrderId() {
        return merchantOrderId;
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public Money getTotalAmount() {
        return totalAmount;
    }

    public List<TransactionStatusInfo> getTransactions() {
        return transactions;
    }
}
