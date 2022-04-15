package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response;

import org.json.JSONObject;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.Money;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class TransactionRefundableDetailsResponse {

    private final UUID transactionId;
    private final Money refundableMoney;
    private final String expiryDatetime;

    public TransactionRefundableDetailsResponse(UUID transactionId,
                                                Money refundableMoney, String expiryDatetime) {
        this.transactionId = transactionId;
        this.refundableMoney = refundableMoney;
        this.expiryDatetime = expiryDatetime;
    }

    public TransactionRefundableDetailsResponse(JSONObject jsonObject) {
        this.transactionId = UUID.fromString(jsonObject.getString("transactionId"));
        this.refundableMoney = Money.fromJson(jsonObject.getJSONObject("refundableMoney"));
        this.expiryDatetime = jsonObject.getString("expiryDatetime");
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public Money getRefundableMoney() {
        return refundableMoney;
    }

    public String getExpiryDatetime() {
        return expiryDatetime;
    }

}
