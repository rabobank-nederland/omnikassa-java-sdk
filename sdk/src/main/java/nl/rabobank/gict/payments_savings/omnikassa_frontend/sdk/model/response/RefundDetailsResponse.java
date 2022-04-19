package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response;

import org.json.JSONObject;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.Money;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.PaymentBrand;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.TransactionStatus;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.VatCategory;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Response from the Rabobank API when a refund is created
 */
public class RefundDetailsResponse {

    private final UUID refundId;
    private final UUID refundTransactionId;
    private final String createdAt;
    private final String updatedAt;
    private final Money refundMoney;
    private final String vatCategory;
    private final String paymentBrand;
    private final String status;
    private final String description;
    private final UUID transactionId;

    public RefundDetailsResponse(UUID refundId,
                                 UUID refundTransactionId,
                                 String createdAt,
                                 String updatedAt,
                                 Money refundMoney,
                                 String vatCategory,
                                 String paymentBrand,
                                 String status,
                                 String description, UUID transactionId) {
        this.refundId = refundId;
        this.refundTransactionId = refundTransactionId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.refundMoney = refundMoney;
        this.vatCategory = vatCategory;
        this.paymentBrand = paymentBrand;
        this.status = status;
        this.description = description;
        this.transactionId = transactionId;
    }

    public RefundDetailsResponse(JSONObject jsonObject) {
        this.refundId = UUID.fromString(jsonObject.getString("refundId"));
        this.refundTransactionId = UUID.fromString(jsonObject.getString("refundTransactionId"));
        this.createdAt = jsonObject.getString("createdAt");
        this.updatedAt = jsonObject.getString("updatedAt");
        this.refundMoney = Money.fromJson(jsonObject.getJSONObject("refundMoney"));
        this.vatCategory = jsonObject.getString("vatCategory");
        this.paymentBrand = jsonObject.getString("paymentBrand");
        this.status = jsonObject.getString("status");
        this.description = jsonObject.getString("description");
        this.transactionId = UUID.fromString(jsonObject.getString("transactionId"));
    }

    public UUID getRefundId() {
        return refundId;
    }

    public UUID getRefundTransactionId() {
        return refundTransactionId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public Money getRefundMoney() {
        return refundMoney;
    }

    public String getVatCategory() {
        return vatCategory;
    }

    public String getPaymentBrand() {
        return paymentBrand;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

}
