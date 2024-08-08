package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk;

/**
 * This class provides the ROFE API environment urls to which the webshops can connect to.
 */
public enum Environment {
    PRODUCTION("https://betalen.rabobank.nl/omnikassa-api"),
    SANDBOX("https://betalen.rabobank.nl/omnikassa-api-sandbox");

    private final String url;

    Environment(String url) {
        this.url = url;
    }

    String getUrl() {
        return url;
    }
}