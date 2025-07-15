package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.RequiredCheckoutFields;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.Language;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details.FastCheckout;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.singletonList;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.Currency.EUR;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details.OrderItemFactory.orderItemFull;

public class IdealFastCheckoutOrderTestFactory {

    public static IdealFastCheckoutOrder any() {

        return new IdealFastCheckoutOrder.Builder()
                .withMerchantOrderId("ORDID123")
                .withOrderItems(singletonList(orderItemFull()))
                .withAmount(Money.fromEuros(EUR, new BigDecimal("4.95")))
                .withLanguage(Language.NL)
                .withDescription("An example description")
                .withMerchantReturnURL("http://localhost/")
                .withShippingCost(Money.fromEuros(EUR, new BigDecimal("1.25")))
                .withPaymentBrandMetaData(getPaymentBrandMetaData())
                .build();
    }

    private static Map<String, Object> getPaymentBrandMetaData() {
        Map<String, Object> paymentBrandMetaData = new HashMap<>();
        Set<RequiredCheckoutFields> requiredCheckoutFields = new LinkedHashSet<>();
        requiredCheckoutFields.add(RequiredCheckoutFields.CUSTOMER_INFORMATION);
        requiredCheckoutFields.add(RequiredCheckoutFields.SHIPPING_ADDRESS);
        FastCheckout fastCheckout = new FastCheckout.Builder()
                .withRequiredCheckoutFields(requiredCheckoutFields)
                .build();
        paymentBrandMetaData.put("fastCheckout", fastCheckout);

        return paymentBrandMetaData;
    }
}
