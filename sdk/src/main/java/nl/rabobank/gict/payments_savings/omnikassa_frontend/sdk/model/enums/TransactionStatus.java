package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums;

/**
 * Represents the current status of a transaction in its lifecycle.
 *
 * The transaction can have one of the following statuses:
 *      NEW - The transaction is created at Smart Pay, but the final status is not yet known.
 *      OPEN - The transaction is created at a third party, but the final status is not yet known.
 *      SUCCESS - The transaction has succeeded.
 *      CANCELLED - The transaction was canceled.
 *      EXPIRED - The transaction has expired.
 *      FAILURE - The transaction has failed.
 *      ACCEPTED - Indicates that the transaction has been successfully processed by the third party.
 * This status is typically used for payment methods where the final settlement is not guaranteed.
 */
public enum TransactionStatus {
    NEW,
    OPEN,
    SUCCESS,
    CANCELLED,
    EXPIRED,
    FAILURE,
    ACCEPTED
}
