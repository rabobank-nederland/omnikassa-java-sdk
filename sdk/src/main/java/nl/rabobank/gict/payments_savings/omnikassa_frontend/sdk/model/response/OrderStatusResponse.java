package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response;

import kong.unirest.json.JSONObject;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.Money;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.utils.CalendarUtils.stringToCalendar;

public class OrderStatusResponse {
    private final String merchantOrderId;
    private final String id;
    private final String poiId;
    private final String orderStatus;
    private final String statusLastUpdatedAt;
    private final Money totalAmount;
    private final List<TransactionInfoOrderStatus> TransactionInfoOrderStatus;

    public OrderStatusResponse(JSONObject jsonObject) {
        JSONObject orderObject = jsonObject.getJSONObject("element");

        this.merchantOrderId = orderObject.getString("merchantOrderId");
        this.id = orderObject.getString("id");
        this.poiId = orderObject.getString("poiId");
        this.orderStatus = orderObject.getString("orderStatus");
        this.statusLastUpdatedAt = orderObject.getString("statusLastUpdatedAt");
        this.totalAmount = Money.fromJsonWithValueInMinorUnits(orderObject.getJSONObject("totalAmount"));
        List<TransactionInfoOrderStatus> transactions = new ArrayList<>();
        if(orderObject.has("transactions")) {
            transactions = StreamSupport.stream(() -> orderObject.getJSONArray("transactions").spliterator(), Spliterator.ORDERED, false)
                                        .map((transactionJsonObject) -> new TransactionInfoOrderStatus((JSONObject) transactionJsonObject))
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
        return orderStatus;
    }

    public String getStatusLastUpdatedAt() { return statusLastUpdatedAt; }

    public Money getTotalAmount() {
        return totalAmount;
    }

    public List<TransactionInfoOrderStatus> getTransactionInfoOrderStatus() {
        return TransactionInfoOrderStatus;
    }
}
