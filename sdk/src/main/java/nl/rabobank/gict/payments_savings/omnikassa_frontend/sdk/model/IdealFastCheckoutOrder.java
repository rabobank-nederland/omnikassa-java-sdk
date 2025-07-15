package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.Language;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.PaymentBrand;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.PaymentBrandForce;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details.OrderItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class IdealFastCheckoutOrder {
    private final String merchantOrderId;
    private final Money amount;
    private final Language language;
    private final String description;
    private final List<OrderItem> orderItems;
    private final PaymentBrand paymentBrand;
    private final PaymentBrandForce paymentBrandForce;
    private final String merchantReturnURL;
    private final Money shippingCost;
    private final boolean skipHppResultPage;
    private final Map<String, Object> paymentBrandMetaData;

    public IdealFastCheckoutOrder(Builder builder) {
        this.merchantOrderId = builder.merchantOrderId;
        this.orderItems = Collections.unmodifiableList(builder.orderItems);
        this.amount = builder.amount;
        this.language = builder.language;
        this.description = builder.description;
        this.paymentBrand = builder.paymentBrand;
        this.paymentBrandForce = builder.paymentBrandForce;
        this.paymentBrandMetaData = builder.paymentBrandMetaData;
        this.skipHppResultPage = builder.skipHppResultPage;
        this.merchantReturnURL = builder.merchantReturnURL;
        this.shippingCost = builder.shippingCost;
    }


    public String getMerchantOrderId() {
        return merchantOrderId;
    }

    public Money getAmount() {
        return amount;
    }

    public Language getLanguage() {
        return language;
    }

    public String getDescription() {
        return description;
    }

    public PaymentBrand getPaymentBrand() {
        return paymentBrand;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public PaymentBrandForce getPaymentBrandForce() {
        return paymentBrandForce;
    }

    public boolean skipHppResultPage() {
        return skipHppResultPage;
    }

    public String getMerchantReturnURL() {
        return merchantReturnURL;
    }

    public Money getShippingCost() {
        return shippingCost;
    }

    public Map<String, Object> getPaymentBrandMetaData() {
        return paymentBrandMetaData;
    }

    public static class Builder {
        private String merchantOrderId;
        private Money amount;
        private Language language;
        private String description;
        private List<OrderItem> orderItems = new ArrayList<>();
        private final PaymentBrand paymentBrand = PaymentBrand.IDEAL;
        private final PaymentBrandForce paymentBrandForce = PaymentBrandForce.FORCE_ALWAYS;
        private String merchantReturnURL;
        private Money shippingCost;
        private Map<String, Object> paymentBrandMetaData;
        private final boolean skipHppResultPage = false;

        /**
         * @param merchantOrderId | Must not be `null`
         *                        | - if shopperBankstatementReference is supplied:
         *                        |   Allows all ascii characters up to a length of 255 characters, if the ID contains more than 255 characters, the extra characters are removed after the 255th character.
         *                        | - else:
         *                        |   Must adhere to the pattern: '[A-Za-z0-9]{1,24}', if the ID contains more than 24 characters, the extra characters are removed after the 24th character.
         * @return Builder
         */
        public IdealFastCheckoutOrder.Builder withMerchantOrderId(String merchantOrderId) {
            this.merchantOrderId = merchantOrderId;
            return this;
        }

        /**
         * @param amount | Must not be `null`
         *               | Must not exceed 99.999,99
         * @return Builder
         */
        public IdealFastCheckoutOrder.Builder withAmount(Money amount) {
            this.amount = amount;
            return this;
        }

        /**
         * @param language | Must not be 'null'
         *                 | Must be a valid Language
         * @return Builder
         */
        public IdealFastCheckoutOrder.Builder withLanguage(Language language) {
            this.language = language;
            return this;
        }

        /**
         * @param description | Optional
         *                    | Must be a valid String
         *                    | Will be truncated to 35 characters longest.
         * @return Builder
         */
        public IdealFastCheckoutOrder.Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        /**
         * @param orderItems | Must not be 'null'
         * @return Builder
         */
        public IdealFastCheckoutOrder.Builder withOrderItems(List<OrderItem> orderItems) {
            this.orderItems = orderItems;
            return this;
        }

        /**
         * @param merchantReturnURL | this must be the Url you want to return to after the payment has been completed
         *                          | Must not be 'null'
         *                          | Must be a valid web address
         * @return Builder
         */
        public IdealFastCheckoutOrder.Builder withMerchantReturnURL(String merchantReturnURL) {
            this.merchantReturnURL = merchantReturnURL;
            return this;
        }

        /**
         * @param shippingCost | Optional
         *                     | Must not exceed 99.999,99
         * @return Builder
         */
        public IdealFastCheckoutOrder.Builder withShippingCost(Money shippingCost) {
            this.shippingCost = shippingCost;
            return this;
        }

        /**
         * Can be used for supplying extra information about the payment brand.
         * @param paymentBrandMetaData | Must not be 'null'
         * @return Builder
         */
        public IdealFastCheckoutOrder.Builder withPaymentBrandMetaData(Map<String, Object> paymentBrandMetaData) {
            this.paymentBrandMetaData = paymentBrandMetaData;
            return this;
        }

        public IdealFastCheckoutOrder build() {
            return new IdealFastCheckoutOrder(this);
        }
    }

}
