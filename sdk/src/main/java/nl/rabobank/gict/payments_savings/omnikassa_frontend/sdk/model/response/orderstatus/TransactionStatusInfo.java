package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.orderstatus;

import kong.unirest.json.JSONObject;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.Money;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.TransactionStatus;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.TransactionType;

import java.time.format.DateTimeFormatter;
import java.time.OffsetDateTime;

/**
 * this class contains details about a transaction related to order status.
 */
public class TransactionStatusInfo {
    private final String id;
    private final String paymentBrand;
    private final TransactionType type;
    private final TransactionStatus status;
    private final Money amount;
    private final OffsetDateTime createdAt;
    private final OffsetDateTime lastUpdatedAt;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    public TransactionStatusInfo(JSONObject jsonObject) {
        this.id = jsonObject.getString("id");
        this.paymentBrand = jsonObject.getString("paymentBrand");
        this.type = jsonObject.getEnum(TransactionType.class, "type");
        this.status = jsonObject.getEnum(TransactionStatus.class, "status");
        this.amount = Money.fromJsonWithValueInMinorUnits(jsonObject.getJSONObject("amount"));
        String createdAtString = jsonObject.getString("createdAt");
        this.createdAt = OffsetDateTime.parse(createdAtString, formatter);
        String lastUpdatedAtString = jsonObject.getString("lastUpdatedAt");
        this.lastUpdatedAt = OffsetDateTime.parse(lastUpdatedAtString, formatter);
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

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getLastUpdatedAt() {
        return lastUpdatedAt;
    }

}
