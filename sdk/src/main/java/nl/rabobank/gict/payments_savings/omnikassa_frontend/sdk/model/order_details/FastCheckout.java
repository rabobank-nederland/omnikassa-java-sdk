package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details;

import lombok.NonNull;
import kong.unirest.json.JSONObject;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.JsonConvertible;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.RequiredCheckoutFields;

import java.util.Set;

public class FastCheckout implements JsonConvertible {
    private final Set<RequiredCheckoutFields> requiredCheckoutFields;

    public FastCheckout(Builder builder) {
        this.requiredCheckoutFields = builder.requestedCheckoutFields;
    }

    public Set<RequiredCheckoutFields> getRequestedCheckoutFields() {
        return requiredCheckoutFields;
    }

    @Override
    public JSONObject asJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("requiredCheckoutFields", requiredCheckoutFields);
        return null;
    }

    public static class Builder {
        private Set<RequiredCheckoutFields> requestedCheckoutFields;

        /**
         * Can be used for supplying extra information about the payment brand.
         *
         * @param requiredCheckoutFields | Must not be `null`
         *                             | -  At least one of the fields must be specified, otherwise an error will be returned.
         *                             |    CUSTOMER_INFORMATION - Request to provide the customer information, including first name, last name, phone number and email.
         *                             |    BILLING_ADDRESS - Request to provide the billing address of the shopper.
         *                             |    SHIPPING_ADDRESS - Request to provide the shipping address of the shopper.
         * @return Builder
         */
        public FastCheckout build(@NonNull Set<RequiredCheckoutFields> requiredCheckoutFields) {
            this.requestedCheckoutFields = requiredCheckoutFields;
            return new FastCheckout(this);
        }
    }
}
