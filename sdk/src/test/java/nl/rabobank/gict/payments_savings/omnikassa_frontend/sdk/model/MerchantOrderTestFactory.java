package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.Language;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.PaymentBrandForce;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;

import static java.util.Collections.singletonList;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.Currency.EUR;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.PaymentBrand.IDEAL;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details.AddressFactory.addressFull;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details.CustomerInformationFactory.customerInformationFull;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details.OrderItemFactory.orderItemFull;

public class MerchantOrderTestFactory {

    public static MerchantOrder any() {
        return defaultBuilder().build();
    }

    public static MerchantOrder includingOptionalFields() {
        return defaultBuilder()
                .withShippingDetail(addressFull())
                .withBillingDetail(addressFull())
                .withOrderItems(singletonList(orderItemFull()))
                .withCustomerInformation(customerInformationFull())
                .withPaymentBrand(IDEAL)
                .withPaymentBrandForce(PaymentBrandForce.FORCE_ALWAYS)
                .withInitiatingParty("LIGHTSPEED")
                .withSkipHppResultPage(true)
                .withPaymentBrandMetaData(getPaymentBrandMetaData())
                .build();
    }

    private static MerchantOrder.Builder defaultBuilder() {
        return new MerchantOrder.Builder()
                .withMerchantOrderId("ORDID123")
                .withAmount(Money.fromEuros(EUR, new BigDecimal("4.95")))
                .withLanguage(Language.NL)
                .withMerchantReturnURL("http://localhost/")
                .withDescription("An example description");
    }

    private static Map<String, String> getPaymentBrandMetaData() {
        return Collections.singletonMap("issuerId", "RABONL2U");
    }
}
