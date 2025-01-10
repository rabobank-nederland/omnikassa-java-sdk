package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.request;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.joda.time.DateTime;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.JsonConvertible;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.MerchantOrder;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.PaymentBrand;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.PaymentBrandForce;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details.Address;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details.CustomerInformation;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details.OrderItem;

import java.util.Map;

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
        Map<String, String> paymentBrandMetaData = merchantOrder.getPaymentBrandMetaData();
        if (paymentBrandMetaData != null && !paymentBrandMetaData.isEmpty()) {
            jsonObject.put("paymentBrandMetaData", paymentBrandMetaData);
        }
        CustomerInformation customerInformation = merchantOrder.getCustomerInformation();
        if (customerInformation != null) {
            JSONObject customerInformationObject = new JSONObject();
            customerInformationObject.put("dateOfBirth", customerInformation.getDateOfBirth());
            customerInformationObject.put("emailAddress", customerInformation.getEmailAddress());
            customerInformationObject.put("gender", customerInformation.getGender());
            customerInformationObject.put("initials", customerInformation.getInitials());
            customerInformationObject.put("telephoneNumber", customerInformation.getTelephoneNumber());
            customerInformationObject.put("fullName", customerInformation.getFullName());
            if (customerInformation.getCompanyName() != null && customerInformation.getCompanyNumber() != null) {
                JSONObject companyInformationObject = new JSONObject();
                companyInformationObject.put("name", customerInformation.getCompanyName());
                companyInformationObject.put("companyNumber", customerInformation.getCompanyNumber());
                companyInformationObject.put("vatNumber", customerInformation.getCompanyVatNumber());
                companyInformationObject.put("countryCode", customerInformation.getCompanyCountryCode());
                customerInformationObject.put("company", companyInformationObject);
            }
            jsonObject.put("customerInformation", customerInformationObject);
        }
        Address billingDetails = merchantOrder.getBillingDetail();
        if (billingDetails != null) {
            jsonObject.put("billingDetail", billingDetails.asJson());
        }
        String initiatingParty = merchantOrder.getInitiatingParty();
        if (initiatingParty != null) {
            jsonObject.put("initiatingParty", merchantOrder.getInitiatingParty());
        }
        jsonObject.put("skipHppResultPage", merchantOrder.skipHppResultPage());
        String shopperBankstatementReference = merchantOrder.getShopperBankstatementReference();
        if (shopperBankstatementReference != null) {
            jsonObject.put("shopperBankstatementReference", shopperBankstatementReference);
        }
        String purchaseOrderReference = merchantOrder.getPurchaseOrderReference();
        if (purchaseOrderReference != null) {
            jsonObject.put("purchaseOrderReference", purchaseOrderReference);

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