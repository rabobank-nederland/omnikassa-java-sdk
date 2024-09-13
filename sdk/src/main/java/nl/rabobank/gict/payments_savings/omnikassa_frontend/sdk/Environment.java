package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk;

/**
 * This class provides the ROFE API environment urls to which the webshops can connect to.
 */
public enum Environment {
    PRODUCTION("https://api.pay.rabobank.nl", "/omnikassa-api"),
    SANDBOX("https://api.pay.rabobank.nl", "/omnikassa-api-sandbox");
    private final String url;

    private final String suffix;

    Environment(String url, String suffix) {
        this.url = url;
        this.suffix = suffix;
    }
    String getUrl() {
        return url;
    }
    public String getSuffix() { return suffix; }
}