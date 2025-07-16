package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.request;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.joda.time.DateTime;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.IdealFastCheckoutOrder;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.JsonConvertible;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.PaymentBrand;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.PaymentBrandForce;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.RequiredCheckoutFields;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details.FastCheckout;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details.OrderItem;

import java.util.Map;
import java.util.Set;

public class IdealFastCheckoutMerchantOrderRequest implements JsonConvertible {

    private final IdealFastCheckoutOrder idealFastCheckoutOrder;
    private final String timestamp;

    public IdealFastCheckoutMerchantOrderRequest(IdealFastCheckoutOrder idealFastCheckoutOrder) {
        this.idealFastCheckoutOrder = idealFastCheckoutOrder;
        this.timestamp = DateTime.now().toString();
    }

    @Override
    public JSONObject asJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("timestamp", timestamp);
        jsonObject.put("merchantOrderId", idealFastCheckoutOrder.getMerchantOrderId());
        jsonObject.put("amount", idealFastCheckoutOrder.getAmount().asJson());
        jsonObject.put("language", idealFastCheckoutOrder.getLanguage().toString());
        jsonObject.put("description", idealFastCheckoutOrder.getDescription());
        jsonObject.put("merchantReturnURL", idealFastCheckoutOrder.getMerchantReturnURL());
        jsonObject.put("shippingCost", idealFastCheckoutOrder.getShippingCost().asJson());
        jsonObject.put("skipHppResultPage", idealFastCheckoutOrder.skipHppResultPage());
        jsonObject.put("orderItems", getOrderItemsAsJson());
        PaymentBrand paymentBrand = idealFastCheckoutOrder.getPaymentBrand();
        if (paymentBrand != null) {
            jsonObject.put("paymentBrand", paymentBrand.name());
        }
        PaymentBrandForce paymentBrandForce = idealFastCheckoutOrder.getPaymentBrandForce();
        if (paymentBrandForce != null) {
            jsonObject.put("paymentBrandForce", paymentBrandForce.name());
        }
        Map<String, Object> paymentBrandMetaData = idealFastCheckoutOrder.getPaymentBrandMetaData();
        if (paymentBrandMetaData != null && !paymentBrandMetaData.isEmpty()) {
            JSONObject paymentBrandMetaDataObject = new JSONObject();
            FastCheckout fastCheckout = (FastCheckout) paymentBrandMetaData.get("fastCheckout");
            if (fastCheckout != null){
                JSONObject fastCheckoutObject = new JSONObject();
                Set<RequiredCheckoutFields> requestedCheckoutFields = fastCheckout.getRequestedCheckoutFields();
                fastCheckoutObject.put("requiredCheckoutFields", requestedCheckoutFields);
                paymentBrandMetaDataObject.put("fastCheckout", fastCheckoutObject);
            }
            jsonObject.put("paymentBrandMetaData", paymentBrandMetaDataObject);
        }

        return jsonObject;
    }

    private JSONArray getOrderItemsAsJson() {
        JSONArray jsonArray = new JSONArray();
        for (OrderItem orderItem : idealFastCheckoutOrder.getOrderItems()) {
            jsonArray.put(orderItem.asJson());
        }
        return jsonArray;
    }
}
