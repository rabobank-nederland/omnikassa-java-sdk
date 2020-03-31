package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.Money;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.VatCategory;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details.OrderItem.Builder;

import java.math.BigDecimal;

import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.Currency.EUR;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.ItemCategory.PHYSICAL;

public class OrderItemFactory {
    public static OrderItem orderItemFull() {
        return defaultOrderItem()
                .withId("1")
                .withVatCategory(VatCategory.LOW)
                .withTax(Money.fromEuros(EUR, new BigDecimal("1")))
                .build();
    }

    public static OrderItem orderItem() {
        return defaultOrderItem().build();
    }

    private static Builder defaultOrderItem() {
        return new Builder()
                .withQuantity(1)
                .withName("Test product")
                .withDescription("Description")
                .withAmount(Money.fromEuros(EUR, new BigDecimal("10")))
                .withItemCategory(PHYSICAL);
    }
}
