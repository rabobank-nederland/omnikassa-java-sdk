package nl.rabobank.gict.payments_savings.omnikassa_frontend.test.webshop.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.IdealFastCheckoutOrder;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.math.BigDecimal.ZERO;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.Currency.EUR;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.Language.NL;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Setter
@Getter
public class WebShopOrder {
    private static final String MERCHANT_RETURN_URL = "http://localhost:8082/webshop/payment/complete";

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final int orderId;
    private BigDecimal amount;
    private LocalDateTime timestamp;
    private UUID omnikassaOrderId;

    private final List<OrderItem> orderItems = new ArrayList<>();

    public WebShopOrder(int orderId) {
        this.orderId = orderId;
    }

    public WebShopOrder(int iterator, BigDecimal amount) {
        this.orderId = iterator;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
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
                                              String preselectedIssuerId,
                                              boolean cof,
                                              String shopperRef,
                                              String initiatingParty,
                                              boolean skipHppResultPage,
                                              String shopperBankstatementReference) {
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
                .withPaymentBrandMetaData(preparePaymentBrandMetaData(preselectedIssuerId,cof))
                .withShopperRef(shopperRef)
                .withInitiatingParty(initiatingParty)
                .withSkipHppResultPage(skipHppResultPage)
                .withShopperBankstatementReference(shopperBankstatementReference)
                .build();
    }

    public IdealFastCheckoutOrder prepareIdealFastCheckoutOrder(Money shippingCost,
                                                                Map<String, Object> paymentBrandMetaData,
                                                                String merchantReturnUrl) {
        return new IdealFastCheckoutOrder.Builder()
                .withMerchantOrderId(String.valueOf(orderId))
                .withOrderItems(orderItems)
                .withMerchantReturnURL(merchantReturnUrl)
                .withAmount(Money.fromEuros(EUR, getTotalPrice()))
                .withLanguage(NL)
                .withDescription("An example description")
                .withShippingCost(shippingCost)
                .withPaymentBrandMetaData(paymentBrandMetaData)
                .build();
    }


    private Map<String, Object> preparePaymentBrandMetaData(String preselectedIssuerId, boolean cof) {
        Map<String, Object> paymentBrandMetaData = new HashMap<>();
        if (isNotBlank(preselectedIssuerId)) {
            paymentBrandMetaData.put("issuerId", preselectedIssuerId);
        }
        paymentBrandMetaData.put("enableCardOnFile", cof);
        return paymentBrandMetaData;
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

    public String getFormattedTimestamp() {
        return FORMATTER.format(timestamp);
    }
}