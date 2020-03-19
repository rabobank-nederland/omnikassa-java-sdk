package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums;

/**
 * This enum houses the different types of payment brands that can be included in the MerchantOrder to restrict
 * the payment brands that the consumer can choose from.
 */
public enum PaymentBrand {
    IDEAL,
    PAYPAL,
    AFTERPAY,
    MASTERCARD,
    VISA,
    BANCONTACT,
    MAESTRO,
    V_PAY,

    /**
     * The CARDS type comprises MASTERCARD, VISA, BANCONTACT, MAESTRO and V_PAY.
     */
    CARDS
}
