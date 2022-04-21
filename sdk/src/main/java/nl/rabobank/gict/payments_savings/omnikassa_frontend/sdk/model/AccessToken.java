package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model;

import kong.unirest.json.JSONObject;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.utils.CalendarUtils;

import java.util.Calendar;

/**
 * this class is used to authorize api calls to the Rabobank
 * the refreshToken is needed to retrieve a new AccessToken
 */
public final class AccessToken {
    private static final double ACCESS_TOKEN_EXPIRATION_MARGIN = 0.01;

    private final String token;
    private final Calendar validUntil;
    private final int durationInMillis;

    public AccessToken(JSONObject jsonObject) {
        this.token = jsonObject.getString("token");
        this.validUntil = CalendarUtils.stringToCalendar(jsonObject.getString("validUntil"));
        this.durationInMillis = jsonObject.getInt("durationInMillis");
    }

    public AccessToken(String token, Calendar validUntil, int durationInMillis) {
        validateArguments(token, validUntil, durationInMillis);
        this.token = token;
        this.validUntil = validUntil;
        this.durationInMillis = durationInMillis;
    }

    private void validateArguments(String token, Calendar validUntil, int durationInMillis) {
        if (token == null || "".equals(token)) {
            throw new IllegalArgumentException("Token cannot be empty");
        }
        if (validUntil == null) {
            throw new IllegalArgumentException("Valid until cannot be empty");
        }
        if (durationInMillis == 0) {
            throw new IllegalArgumentException("Duration in milliseconds cannot be zero");
        }
    }

    public String getToken() {
        return this.token;
    }

    public Calendar getValidUntil() {
        return this.validUntil;
    }

    public int getDurationInMillis() {
        return this.durationInMillis;
    }

    public boolean isNotExpired() {
        return !isExpired();
    }

    boolean isExpired() {
        Calendar currentDate = Calendar.getInstance();
        long timeLeft = getValidUntil().getTimeInMillis() - currentDate.getTimeInMillis();
        return (timeLeft / ((double) this.getDurationInMillis())) < ACCESS_TOKEN_EXPIRATION_MARGIN;
    }
}
