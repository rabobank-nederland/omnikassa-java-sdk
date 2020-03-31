package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.request;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.JsonConvertible;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.MerchantOrder;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.PaymentBrand;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.PaymentBrandForce;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details.Address;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details.CustomerInformation;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details.OrderItem;

/**
 * this class represents an order/transaction of money.
 * Note: merchantOrderId is identification/'name' of the order, hence "abc1234"
 */
public class MerchantOrderRequest implements JsonConvertible {

    private final MerchantOrder merchantOrder;
    private String timestamp;

    public MerchantOrderRequest(MerchantOrder merchantOrder) {
        this.merchantOrder = merchantOrder;
        this.timestamp = DateTime.now().toString();
    }

    @Override
    public JSONObject asJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("timestamp", timestamp);
        jsonObject.put("merchantOrderId", merchantOrder.getMerchantOrderId());
        jsonObject.put("amount", merchantOrder.getAmount().asJson());
        jsonObject.put("language", merchantOrder.getLanguage().toString());
        jsonObject.put("description", merchantOrder.getDescription());
        jsonObject.put("merchantReturnURL", merchantOrder.getMerchantReturnURL());
        jsonObject.put("orderItems", getOrderItemsAsJson());
        Address shippingDetails = merchantOrder.getShippingDetail();
        if (shippingDetails != null) {
            jsonObject.put("shippingDetail", shippingDetails.asJson());
        }
        PaymentBrand paymentBrand = merchantOrder.getPaymentBrand();
        if (paymentBrand != null) {
            jsonObject.put("paymentBrand", paymentBrand.name());
        }
        PaymentBrandForce paymentBrandForce = merchantOrder.getPaymentBrandForce();
        if (paymentBrandForce != null) {
            jsonObject.put("paymentBrandForce", paymentBrandForce.name());
        }
        CustomerInformation customerInformation = merchantOrder.getCustomerInformation();
        if (customerInformation != null) {
            jsonObject.put("customerInformation", customerInformation.asJson());
        }
        Address billingDetails = merchantOrder.getBillingDetail();
        if (billingDetails != null) {
            jsonObject.put("billingDetail", billingDetails.asJson());
        }
        String initiatingParty = merchantOrder.getInitiatingParty();
        if (initiatingParty != null) {
            jsonObject.put("initiatingParty", merchantOrder.getInitiatingParty());
        }
        return jsonObject;
    }

    private JSONArray getOrderItemsAsJson() {
        JSONArray jsonArray = new JSONArray();
        for (OrderItem orderItem : merchantOrder.getOrderItems()) {
            jsonArray.put(orderItem.asJson());
        }
        return jsonArray;
    }
}