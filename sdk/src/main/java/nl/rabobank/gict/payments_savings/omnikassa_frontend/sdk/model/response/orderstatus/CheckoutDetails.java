package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.orderstatus;

import kong.unirest.json.JSONObject;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details.Address;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.utils.JSONFieldUtil;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.utils.JSONFieldUtil.isDefined;

public class CheckoutDetails {
    private static final String CUSTOMER_INFORMATION_FILED = "customerInformation";
    private static final String BILLING_ADDRESS_FIELD = "billingAddress";
    private static final String SHIPPING_ADDRESS_FIELD = "shippingAddress";

    private final RequestedCustomerInformation customerInformation;
    private final Address billingAddress;
    private final Address shippingAddress;

    public CheckoutDetails(Builder builder) {
        this.customerInformation = builder.customerInformation;
        this.billingAddress = builder.billingAddress;
        this.shippingAddress = builder.shippingAddress;
    }

    public static CheckoutDetails fromJson(JSONObject jsonObject) {
        Builder builder = new Builder();
        if (isDefined(jsonObject, CUSTOMER_INFORMATION_FILED)) {
            builder.withCustomerInformation(new RequestedCustomerInformation(JSONFieldUtil.getJSONObject(jsonObject, CUSTOMER_INFORMATION_FILED)));
        }
        if (isDefined(jsonObject, BILLING_ADDRESS_FIELD)){
            builder.withBillingAddress(Address.fromJson(JSONFieldUtil.getJSONObject(jsonObject, BILLING_ADDRESS_FIELD)));
        }
        if (isDefined(jsonObject, SHIPPING_ADDRESS_FIELD)){
            builder.withShippingAddress(Address.fromJson(JSONFieldUtil.getJSONObject(jsonObject, SHIPPING_ADDRESS_FIELD)));
        }

        return builder.build();
    }

    public static class Builder {
        private RequestedCustomerInformation customerInformation;
        private Address billingAddress;
        private Address shippingAddress;

        public CheckoutDetails build() {
            return new CheckoutDetails(this);
        }

        public Builder withCustomerInformation(RequestedCustomerInformation customerInformation) {
            this.customerInformation = customerInformation;
            return this;
        }

        public Builder withBillingAddress(Address billingAddress) {
            this.billingAddress = billingAddress;
            return this;
        }

        public Builder withShippingAddress(Address shippingAddress) {
            this.shippingAddress = shippingAddress;
            return this;
        }
    }


    public RequestedCustomerInformation getCustomerInformation() { return customerInformation; }

    public Address getBillingAddress() { return billingAddress; }

    public Address getShippingAddress() { return shippingAddress; }
}
