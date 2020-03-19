package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums;

/**
 * This enum provides constants for the paymentBrandForce field of a MerchantOrder. This field is used in
 * combination with the paymentBrand field.
 * When paymentBrandForce is set to FORCE_ONCE then the supplied paymentBrand is only enforced in the customer's first
 * payment attempt. If the payment was not successful then the consumer is allowed to select an alternative
 * payment brand in the Hosted Payment Pages.
 * When paymentBrandForce is set to FORCE_ALWAYS then the consumer is not allowed to select an alternative
 * payment brand, the customer is restricted to use the provided paymentBrand for all payment attempts.
 */
public enum PaymentBrandForce {
    FORCE_ONCE,
    FORCE_ALWAYS
}
