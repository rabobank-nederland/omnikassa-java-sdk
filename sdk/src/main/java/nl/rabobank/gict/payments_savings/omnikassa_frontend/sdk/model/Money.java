package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model;

import org.json.JSONObject;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.Currency;

import java.math.BigDecimal;

import static java.math.RoundingMode.UNNECESSARY;

/**
 * this class represents money. The amount is stored as a BigDecimal, and is limited by two decimal places.
 * Money is stored as euros, 1 EUR = {@code new Money(Currency.EUR,new BigDecimal("1.00"))}.
 * please use the String constructor when making a new BigDecimal.
 */
public final class Money implements JsonConvertible {
    private static final int SCALE = 2;
    private static final BigDecimal TO_CENTS_FACTOR = new BigDecimal(100);

    private final Currency currency;
    private final BigDecimal amount;

    private Money(Currency currency, BigDecimal amount) {
        this.currency = currency;
        this.amount = amount;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    /**
     * @param currency | Must not be 'null'
     *                 | Must be a valid Currency
     * @param amount   | Must not be 'null'
     *                 | Must be greater than zero
     * @return new instance of Money
     */
    public static Money fromEuros(Currency currency, BigDecimal amount) {
        checkAmount(amount);
        return new Money(currency, amount);
    }

    public static Money fromJson(JSONObject jsonObject) {
        Currency currency = jsonObject.getEnum(Currency.class, "currency");
        BigDecimal amount = parseAmount(jsonObject.getString("amount"));
        return new Money(currency, amount);
    }

    private static void checkAmount(BigDecimal amount) {
        if (getNumberOfDecimalPlaces(amount) > SCALE) {
            throw new IllegalArgumentException("amount must have at most 2 decimal places, and must be a valid number");
        }
    }

    private static int getNumberOfDecimalPlaces(BigDecimal bigDecimal) {
        return Math.max(0, bigDecimal.stripTrailingZeros().scale());
    }

    private static BigDecimal parseAmount(String amountString) {
        BigDecimal amountBigDecimal = new BigDecimal(amountString);
        amountBigDecimal = amountBigDecimal.movePointLeft(SCALE);
        checkAmount(amountBigDecimal);
        return amountBigDecimal;
    }

    public long getAmountInCents() {
        BigDecimal rounded = amount.setScale(SCALE, UNNECESSARY);
        return rounded.multiply(TO_CENTS_FACTOR).longValueExact();
    }

    @Override
    public JSONObject asJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("currency", currency.toString());
        jsonObject.put("amount", String.valueOf(getAmountInCents()));
        return jsonObject;
    }
}
