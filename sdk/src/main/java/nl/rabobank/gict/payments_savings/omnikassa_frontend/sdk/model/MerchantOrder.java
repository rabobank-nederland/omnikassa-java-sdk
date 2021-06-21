package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.Language;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.PaymentBrand;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.PaymentBrandForce;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details.Address;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details.CustomerInformation;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details.OrderItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class MerchantOrder {

    private final String merchantOrderId;
    private final Money amount;
    private final Language language;
    private final String description;
    private final String merchantReturnURL;
    private final List<OrderItem> orderItems;
    private final Address shippingDetails;
    private final Address billingDetails;
    private final CustomerInformation customerInformation;
    private final PaymentBrand paymentBrand;
    private final PaymentBrandForce paymentBrandForce;
    private final Map<String, String> paymentBrandMetaData;
    private final boolean skipHppResultPage;

    private final String initiatingParty;

    private MerchantOrder(Builder builder) {
        this.merchantOrderId = builder.merchantOrderId;
        this.amount = builder.amount;
        this.language = builder.language;
        this.description = builder.description;
        this.merchantReturnURL = builder.merchantReturnURL;
        this.orderItems = Collections.unmodifiableList(builder.orderItems);
        this.shippingDetails = builder.shippingDetails;
        this.billingDetails = builder.billingDetails;
        this.customerInformation = builder.customerInformation;
        this.paymentBrand = builder.paymentBrand;
        this.paymentBrandForce = builder.paymentBrandForce;
        this.initiatingParty = builder.initiatingParty;
        this.paymentBrandMetaData = builder.paymentBrandMetaData;
        this.skipHppResultPage = builder.skipHppResultPage;
    }
    public String getInitiatingParty() {
        return initiatingParty;
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

    public String getMerchantReturnURL() {
        return merchantReturnURL;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public Address getShippingDetail() {
        return shippingDetails;
    }

    public Address getBillingDetail() {
        return billingDetails;
    }

    public PaymentBrand getPaymentBrand() {
        return paymentBrand;
    }

    public PaymentBrandForce getPaymentBrandForce() {
        return paymentBrandForce;
    }

    public Map<String, String> getPaymentBrandMetaData() {
        return paymentBrandMetaData;
    }

    public CustomerInformation getCustomerInformation() {
        return customerInformation;
    }

    public boolean skipHppResultPage() {
        return skipHppResultPage;
    }

    public static class Builder {

        private String merchantOrderId;
        private Money amount;
        private Language language;
        private String description;
        private String merchantReturnURL;
        private List<OrderItem> orderItems = new ArrayList<>();
        private Address shippingDetails;
        private Address billingDetails;
        private CustomerInformation customerInformation;
        private PaymentBrand paymentBrand;
        private PaymentBrandForce paymentBrandForce;
        private Map<String, String> paymentBrandMetaData = Collections.emptyMap();
        private String initiatingParty;
        private boolean skipHppResultPage = false;

        /**
         * @param merchantOrderId | Must not be `null`
         *                        | Must contain only alphanumeric characters
         *                        | Will be truncated to 35 characters longest.
         * @return Builder
         */
        public Builder withMerchantOrderId(String merchantOrderId) {
            this.merchantOrderId = merchantOrderId;
            return this;
        }

        /**
         * @param amount | Must not be `null`
         *               | Must not exceed 99.999,99
         * @return Builder
         */
        public Builder withAmount(Money amount) {
            this.amount = amount;
            return this;
        }

        /**
         * @param language | Must not be 'null'
         *                 | Must be a valid Language
         * @return Builder
         */
        public Builder withLanguage(Language language) {
            this.language = language;
            return this;
        }

        /**
         * @param description | Optional
         *                    | Must be a valid String
         *                    | Will be truncated to 35 characters longest.
         * @return Builder
         */
        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        /**
         * @param merchantReturnURL | this must be the Url you want to return to after the payment has been completed
         *                          | Must not be 'null'
         *                          | Must be a valid web address
         * @return Builder
         */
        public Builder withMerchantReturnURL(String merchantReturnURL) {
            this.merchantReturnURL = merchantReturnURL;
            return this;
        }

        /**
         * @param orderItems | Must not be 'null'
         * @return Builder
         */
        public Builder withOrderItems(List<OrderItem> orderItems) {
            this.orderItems = orderItems;
            return this;
        }

        /**
         * @param shippingDetails | Must not be 'null'
         * @return Builder
         */
        public Builder withShippingDetail(Address shippingDetails) {
            this.shippingDetails = shippingDetails;
            return this;
        }

        /**
         * @param billingDetails | Optional
         * @return Builder
         */
        public Builder withBillingDetail(Address billingDetails) {
            this.billingDetails = billingDetails;
            return this;
        }

        /**
         * @param paymentBrand | Optional
         * @return Builder
         */
        public Builder withPaymentBrand(PaymentBrand paymentBrand) {
            this.paymentBrand = paymentBrand;
            return this;
        }

        /**
         * @param paymentBrandForce | Required when paymentBrand is supplied
         * @return Builder
         */
        public Builder withPaymentBrandForce(PaymentBrandForce paymentBrandForce) {
            this.paymentBrandForce = paymentBrandForce;
            return this;
        }

        /**
         * Can be used for supplying extra information about the payment brand.
         * @param paymentBrandMetaData | Optional
         * @return Builder
         */
        public Builder withPaymentBrandMetaData(Map<String, String> paymentBrandMetaData) {
            this.paymentBrandMetaData = paymentBrandMetaData;
            return this;
        }

        /**
         * @param customerInformation | Optional
         * @return Builder
         */
        public Builder withCustomerInformation(CustomerInformation customerInformation) {
            this.customerInformation = customerInformation;
            return this;
        }

        /**
         * @param initiatingParty | Optional
         * @return Builder
         */
        public Builder withInitiatingParty(String initiatingParty) {
            this.initiatingParty = initiatingParty;
            return this;
        }

        /**
         * Set to true if you want the customer to be immediately redirected to your webshop after the payment has concluded.
         * @param skipHppResultPage | Optional
         * @return Builder
         */
        public Builder withSkipHppResultPage(boolean skipHppResultPage) {
            this.skipHppResultPage = skipHppResultPage;
            return this;
        }

        public MerchantOrder build() {
            return new MerchantOrder(this);
        }
    }
}
