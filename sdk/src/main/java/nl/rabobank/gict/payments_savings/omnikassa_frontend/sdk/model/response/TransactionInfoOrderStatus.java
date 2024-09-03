package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response;

import kong.unirest.json.JSONObject;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.Money;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.TransactionStatus;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.TransactionType;

import java.util.Optional;

/**
 * this class contains details about a transaction related to order status.
 */
public class TransactionInfoOrderStatus {
    private final String id;
    private final String paymentBrand;
    private final TransactionType type;
    private final TransactionStatus status;
    private final Money amount;
    private final String createdAt;
    private final String lastUpdatedAt;

    public TransactionInfoOrderStatus(JSONObject jsonObject) {
        this.id = jsonObject.getString("id");
        this.paymentBrand = jsonObject.getString("paymentBrand");
        this.type = jsonObject.getEnum(TransactionType.class, "type");
        this.status = jsonObject.getEnum(TransactionStatus.class, "status");
        this.amount = Money.fromJsonWithValueInMinorUnits(jsonObject.getJSONObject("amount"));
        this.createdAt = jsonObject.getString("createdAt");
        this.lastUpdatedAt = jsonObject.getString("lastUpdatedAt");
    }

    public String getId() {
        return id;
    }

    public String getPaymentBrand() {
        return paymentBrand;
    }

    public TransactionType getType() {
        return type;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public Money getAmount() {
        return amount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getLastUpdatedAt() {
        return lastUpdatedAt;
    }

}
