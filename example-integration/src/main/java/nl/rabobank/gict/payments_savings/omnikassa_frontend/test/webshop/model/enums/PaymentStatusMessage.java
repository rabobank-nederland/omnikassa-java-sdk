package nl.rabobank.gict.payments_savings.omnikassa_frontend.test.webshop.model.enums;

public enum PaymentStatusMessage {
    COMPLETED("congratulations, your payment was a success"),
    CANCELLED("You cancelled your payment"),
    IN_PROGRESS("We can not verify your purchase at this moment"),
    EXPIRED("We're sorry your payment session is expired");

    private final String message;

    PaymentStatusMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
