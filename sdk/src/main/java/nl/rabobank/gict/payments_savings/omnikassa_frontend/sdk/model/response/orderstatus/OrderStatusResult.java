package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.orderstatus;

import java.util.Objects;
import kong.unirest.json.JSONObject;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.Money;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.utils.JSONFieldUtil;
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
    private final CheckoutDetails checkoutDetails;

    public OrderStatusResult(Builder builder) {
        this.merchantOrderId = builder.merchantOrderId;
        this.id = builder.id;
        this.status = builder.status;
        this.totalAmount = builder.totalAmount;
        this.transactions = builder.transactions;
        this.checkoutDetails = builder.checkoutDetails;
    }

    static OrderStatusResult fromJson(JSONObject jsonObject) {
        Builder builder = new Builder()
                .withMerchantOrderId(jsonObject.getString( "merchantOrderId"))
                .withId(jsonObject.getString("id"))
                .withStatus(jsonObject.getString("status"))
                .withTotalAmount(Money.fromJson(Objects.requireNonNull(JSONFieldUtil.getJSONObject(jsonObject, "totalAmount"))));
        List<TransactionStatusInfo> transactions;
        if(jsonObject.has("transactions")) {
            transactions = StreamSupport.stream(() -> jsonObject.optJSONArray("transactions").spliterator(), Spliterator.ORDERED, false)
                                        .map(transactionJsonObject -> new TransactionStatusInfo((JSONObject) transactionJsonObject))
                                        .collect(Collectors.toList());
            builder.withTransactions(Collections.unmodifiableList(transactions));
        }
        if (JSONFieldUtil.isDefined(jsonObject, "checkoutDetails")) {
            builder.withCheckoutDetails(CheckoutDetails.fromJson(jsonObject.getJSONObject("checkoutDetails")));
        }

        return builder.build();
    }

    public static class Builder {
        private String merchantOrderId;
        private String id;
        private String status;
        private Money totalAmount;
        private List<TransactionStatusInfo> transactions;
        private CheckoutDetails checkoutDetails;
        public Builder withMerchantOrderId(String merchantOrderId) {
            this.merchantOrderId = merchantOrderId;
            return this;
        }
        public Builder withId(String id) {
            this.id = id;
            return this;
        }
        public Builder withStatus(String status) {
            this.status = status;
            return this;
        }
        public Builder withTotalAmount(Money totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }
        public Builder withTransactions(List<TransactionStatusInfo> transactions) {
            this.transactions = transactions;
            return this;
        }
        public Builder withCheckoutDetails(CheckoutDetails checkoutDetails) {
            this.checkoutDetails = checkoutDetails;
            return this;
        }
        public OrderStatusResult build() {
            return new OrderStatusResult(this);
        }
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

    public CheckoutDetails getCheckoutDetails() {
        return checkoutDetails;
    }
}
