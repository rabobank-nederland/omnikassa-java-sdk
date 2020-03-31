package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details;

import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.JsonFileConverter;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.Money;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.ItemCategory;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.Money.fromEuros;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.Currency.EUR;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.ItemCategory.DIGITAL;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.ItemCategory.PHYSICAL;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.VatCategory.LOW;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details.OrderItemFactory.orderItem;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details.OrderItemFactory.orderItemFull;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class OrderItemTest {
    private OrderItem orderItem = orderItem();
    private OrderItem orderItemFull = orderItemFull();
    private JsonFileConverter converter = new JsonFileConverter();

    @Test
    public void testFields() {
        assertRequiredFields(orderItem);
    }

    @Test
    public void testFieldsFull() {
        assertRequiredFields(orderItemFull);
        assertOptionalFields(orderItemFull);
    }

    @Test
    public void testAsJson() {
        JSONAssert.assertEquals(converter.convert("order_item.json"), orderItem.asJson(), true);
    }

    @Test
    public void testAsJsonFull() {
        JSONAssert.assertEquals(converter.convert("order_item_full.json"), orderItemFull.asJson(), true);
    }

    @Test
    public void tesOldStyleConstructor() {
        Money amount = fromEuros(EUR, valueOf(1234L));
        Money tax = fromEuros(EUR, valueOf(10L));

        OrderItem item = new OrderItem(10, "name", "description", amount, tax, DIGITAL);

        assertThat(item.getAmount(), is(amount));
        assertThat(item.getCategory(), is(DIGITAL));
        assertThat(item.getTax(), is(tax));
        assertThat(item.getName(), is("name"));
        assertThat(item.getDescription(), is("description"));
        assertThat(item.getQuantity(), is(10));
        assertThat(item.getId(), nullValue());
        assertThat(item.getVatCategory(), nullValue());
    }

    private void assertRequiredFields(OrderItem orderItem) {
        assertThat(orderItem.getCategory(), is(PHYSICAL));
        assertThat(orderItem.getAmount().getCurrency(), is(EUR));
        assertThat(orderItem.getAmount().getAmountInCents(), is(1000L));
        assertThat(orderItem.getDescription(), is("Description"));
        assertThat(orderItem.getName(), is("Test product"));
        assertThat(orderItem.getQuantity(), is(1));
    }

    private void assertOptionalFields(OrderItem orderItem) {
        assertThat(orderItem.getTax().getCurrency(), is(EUR));
        assertThat(orderItem.getTax().getAmountInCents(), is(100L));
        assertThat(orderItem.getId(), is("1"));
        assertThat(orderItem.getVatCategory(), is(LOW));
    }
}
