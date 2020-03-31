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
import java.util.List;

import static java.math.BigDecimal.ZERO;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.Currency.EUR;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.Language.NL;


public class WebShopOrder {
    private static final String MERCHANT_RETURN_URL = "http://localhost:8082/webshop/payment/complete";

    private int orderId;

    private List<OrderItem> orderItems = new ArrayList<>();

    public WebShopOrder(int orderId) {
        this.orderId = orderId;
    }

    public void addItem(OrderItem item) {
        orderItems.add(item);
    }

    public void clear() {
        orderItems.clear();
    }

    public MerchantOrder prepareMerchantOrder(CustomerInformation customerInformation,
                                              Address shippingDetails,
                                              Address billingDetails,
                                              PaymentBrand paymentBrand,
                                              PaymentBrandForce paymentBrandForce,
                                              String initiatingParty) {
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
                .withInitiatingParty(initiatingParty)
                .build();
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