package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk;

import lombok.Getter;

/**
 * This class provides the ROFE API environment urls to which the webshops can connect to.
 */
@Getter
public enum Environment {
    PRODUCTION("https://api.pay.rabobank.nl"),
    SANDBOX("https://api.pay-sandbox.rabobank.nl");
    private final String url;

    Environment(String url) {
        this.url = url;
    }
}