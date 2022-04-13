package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details;

import kong.unirest.json.JSONObject;
import org.junit.Test;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.Money;

import java.math.BigDecimal;

import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.Currency.EUR;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class MoneyTest {

    @Test
    public void testFromEuros() {
        Money money = Money.fromEuros(EUR, new BigDecimal("4.95"));

        assertThat(money.getAmountInCents(), is(495L));
        assertThat(money.getCurrency(), is(EUR));
        assertThat(money.getAmount(), is(new BigDecimal("4.95")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromEuros_TooMuchDecimals() {
        Money.fromEuros(EUR, new BigDecimal("1.234"));
    }

    @Test
    public void jsonConstructor() {
        Money money = Money.fromJson(getValidMoneyJson());

        assertThat(money.getAmountInCents(), is(100L));
        assertThat(money.getCurrency(), is(EUR));
        assertThat(money.getAmount(), is(new BigDecimal("1.00")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void jsonConstructor_Should_ThrowException_When_AmountIsNotInCents() {
        Money.fromJson(getInvalidMoneyAsJson());
    }

    @Test
    public void asJson_Should_ReturnCorrectJsonObject() {
        Money money = Money.fromEuros(EUR, new BigDecimal("1.00"));

        assertEquals(getValidMoneyJson(), money.asJson());
    }

    private JSONObject getValidMoneyJson() {
        return new JSONObject("{'amount':'100','currency':'EUR'}");
    }

    private JSONObject getInvalidMoneyAsJson() {
        return new JSONObject("{currency:'EUR',amount:'59.9'}");
    }
}
