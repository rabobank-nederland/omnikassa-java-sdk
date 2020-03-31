package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.connector;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.AccessToken;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.utils.CalendarUtils;

import java.util.Calendar;

import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.connector.TokenProvider.FieldName.ACCESS_TOKEN;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.connector.TokenProvider.FieldName.ACCESS_TOKEN_DURATION;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.connector.TokenProvider.FieldName.ACCESS_TOKEN_VALID_UNTIL;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.connector.TokenProvider.FieldName.REFRESH_TOKEN;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * this abstract class is to be implemented/extended.
 * The token provider is needed to store and retrieve the values of a token in key value pairs.
 * The abstract class requires that the `getValue(key)` and the `setValue(key, value)` functions are implemented.
 * The implementation is entirely up to the developer. For example the values can be persisted in a database and cached.
 * The only thing to note, however, is that the refresh token is provided by *your* implementation and should have been supplied by the Rabobank together with this SDK release.
 */
public abstract class TokenProvider {
    public enum FieldName {
        REFRESH_TOKEN,
        ACCESS_TOKEN,
        ACCESS_TOKEN_VALID_UNTIL,
        ACCESS_TOKEN_DURATION
    }

    private AccessToken accessToken;

    public final String getAccessToken() {
        if (accessToken == null && hasAccessToken()) {
            accessToken = reCreateAccessToken();
        }

        if (accessToken != null && accessToken.isNotExpired()) {
            return accessToken.getToken();
        } else {
            return null;
        }
    }

    public final boolean hasNoValidAccessToken() {
        return getAccessToken() == null;
    }

    public final String getRefreshToken() {
        return getValue(REFRESH_TOKEN);
    }

    public final void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
        setValue(ACCESS_TOKEN, accessToken.getToken());
        setValue(ACCESS_TOKEN_VALID_UNTIL, CalendarUtils.calendarToString(accessToken.getValidUntil()));
        setValue(ACCESS_TOKEN_DURATION, String.valueOf(accessToken.getDurationInMillis()));
    }

    private AccessToken reCreateAccessToken() {
        String token = getValue(ACCESS_TOKEN);
        String validUntil = getValue(ACCESS_TOKEN_VALID_UNTIL);
        String durationInMillis = getValue(ACCESS_TOKEN_DURATION);
        return new AccessToken(token, CalendarUtils.stringToCalendar(validUntil), Integer.parseInt(durationInMillis));
    }

    private boolean hasAccessToken() {
        String token = getValue(ACCESS_TOKEN);
        String validUntil = getValue(ACCESS_TOKEN_VALID_UNTIL);
        Calendar validUntilCalendar = CalendarUtils.stringToCalendar(validUntil);
        String durationInMillis = getValue(ACCESS_TOKEN_DURATION);
        return isNotBlank(token) && isNotBlank(validUntil) && isNotBlank(durationInMillis) && validUntilCalendar != null;
    }

    /**
     * This function should retrieve the appropriate value for the key
     *
     * @param key FieldName of the value which should be retrieved
     * @return the string value of the stored key
     */
    protected abstract String getValue(FieldName key);

    /**
     * This function should store the combination of a key and a value
     *
     * @param key   FieldName of the value which should be retrieved
     * @param value String value of max 1024 characters to be stored
     */
    protected abstract void setValue(FieldName key, String value);

}
