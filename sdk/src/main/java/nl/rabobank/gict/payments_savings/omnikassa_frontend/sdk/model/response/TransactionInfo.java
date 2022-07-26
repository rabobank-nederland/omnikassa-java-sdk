package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response;

import kong.unirest.json.JSONObject;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.Money;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.PaymentBrand;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.TransactionStatus;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.TransactionType;

import java.util.Arrays;
import java.util.List;

/**
 * this class contains details about a transaction, these details can be used to identify specific transaction within the order.
 */
public final class TransactionInfo {

    private final String id;
    private final PaymentBrand paymentBrand;
    private final TransactionType type;
    private final TransactionStatus status;
    private final Money amount;
    private final Money confirmedAmount;
    private final String startTime;
    private final String lastUpdateTime;

    public TransactionInfo(JSONObject jsonObject) {
        this.id = jsonObject.getString("id");
        this.paymentBrand = jsonObject.getEnum(PaymentBrand.class, "paymentBrand");
        this.type = jsonObject.getEnum(TransactionType.class, "type");
        this.status = jsonObject.getEnum(TransactionStatus.class, "status");
        this.amount = Money.fromJson(jsonObject.getJSONObject("amount"));
        if (jsonObject.has("confirmedAmount")) {
            this.confirmedAmount = Money.fromJson(jsonObject.getJSONObject("confirmedAmount"));
        } else {
            this.confirmedAmount = null;
        }
        this.startTime = jsonObject.getString("startTime");
        this.lastUpdateTime = jsonObject.getString("lastUpdateTime");
    }

    public String getId() {
        return id;
    }

    public PaymentBrand getPaymentBrand() {
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

    public Money getConfirmedAmount() {
        return confirmedAmount;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public List<String> getSignatureData() {
        return Arrays.asList(id,
                             paymentBrand.name(),
                             type.name(),
                             status.name(),
                             amount.getCurrency().name(),
                             String.valueOf(amount.getAmountInCents()),
                             confirmedAmount != null ? confirmedAmount.getCurrency().name() : null,
                             confirmedAmount != null ? String.valueOf(confirmedAmount.getAmountInCents()) : null,
                             startTime,
                             lastUpdateTime);
    }

}
