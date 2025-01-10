package nl.rabobank.gict.payments_savings.omnikassa_frontend.test.webshop.model;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.MerchantOrder;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.MerchantOrder.Builder;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.Money;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.PaymentBrand;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.PaymentBrandForce;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details.Address;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details.CustomerInformation;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details.OrderItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.math.BigDecimal.ZERO;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.Currency.EUR;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.Language.NL;
import static org.apache.commons.lang3.StringUtils.isBlank;


public class WebShopOrder {
    private static final String MERCHANT_RETURN_URL = "http://localhost:8082/webshop/payment/complete";

    private final int orderId;

    private final List<OrderItem> orderItems = new ArrayList<>();

    public WebShopOrder(int orderId) {
        this.orderId = orderId;
    }

    public void addItem(OrderItem item) {
        orderItems.add(item);
    }

    public void clear() {
        orderItems.clear();
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public MerchantOrder prepareMerchantOrder(CustomerInformation customerInformation,
                                              Address shippingDetails,
                                              Address billingDetails,
                                              PaymentBrand paymentBrand,
                                              PaymentBrandForce paymentBrandForce,
                                              String preselectedIssuerId,
                                              String initiatingParty,
                                              boolean skipHppResultPage,
                                              String shopperBankstatementReference,
                                              String purchaseOrderReference) {
        return new Builder()
                .withMerchantOrderId(String.valueOf(orderId))
                .withAmount(Money.fromEuros(EUR, getTotalPrice()))
                .withLanguage(NL)
                .withMerchantReturnURL(MERCHANT_RETURN_URL)
                .withDescription("An example description")
                .withShippingDetail(shippingDetails)
                .withBillingDetail(billingDetails)
                .withCustomerInformation(customerInformation)
                .withOrderItems(orderItems)
                .withPaymentBrand(paymentBrand)
                .withPaymentBrandForce(paymentBrandForce)
                .withPaymentBrandMetaData(preparePaymentBrandMetaData(preselectedIssuerId))
                .withInitiatingParty(initiatingParty)
                .withSkipHppResultPage(skipHppResultPage)
                .withShopperBankstatementReference(shopperBankstatementReference)
                .withPurchaseOrderReference(purchaseOrderReference)
                .build();
    }

    private Map<String, String> preparePaymentBrandMetaData(String preselectedIssuerId) {
        if (isBlank(preselectedIssuerId)) {
            return null;
        }

        return Collections.singletonMap("issuerId", preselectedIssuerId);
    }

    private BigDecimal getTotalPrice() {
        BigDecimal sum = ZERO;
        for (OrderItem item : orderItems) {
            BigDecimal itemPrice = item.getAmount().getAmount();
            itemPrice = itemPrice.multiply(new BigDecimal(item.getQuantity()));
            sum = sum.add(itemPrice);
        }
        return sum;
    }
}