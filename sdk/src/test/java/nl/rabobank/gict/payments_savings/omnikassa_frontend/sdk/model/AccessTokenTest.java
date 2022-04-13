package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.utils.CalendarUtils;
import kong.unirest.json.JSONException;
import kong.unirest.json.JSONObject;
import org.junit.Test;

import java.util.Calendar;

import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.utils.CalendarUtils.calendarToString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AccessTokenTest {
    @Test
    public void testAccessToken() {
        AccessToken accessToken = new AccessTokenBuilder().build();

        assertThat(accessToken.getToken(), is("token"));
        assertThat(accessToken.getDurationInMillis(), is(28800000));
        assertThat(calendarToString(accessToken.getValidUntil()), is("2016-09-22T10:10:04.848+0200"));

        assertThat(accessToken.isNotExpired(), is(false));
    }

    @Test
    public void testAccessToken_StillValidTomorrow() {
        AccessToken accessToken = new AccessTokenBuilder().withValidUntilTomorrow().build();

        assertThat(accessToken.isNotExpired(), is(true));
    }

    @Test
    public void testAccessToken_AssumeSomeClockDrift() {
        AccessToken accessToken = new AccessTokenBuilder().withValidUntilNow().build();

        // if a access token is about to expire than we assume its expired, so we force a refresh
        assertThat(accessToken.isExpired(), is(true));
    }

    @Test
    public void testAccessToken_StillValidForFiveMinutes() {
        Calendar expiredInFiveMinutes = Calendar.getInstance();
        expiredInFiveMinutes.add(Calendar.MINUTE, 5);

        AccessToken accessToken = new AccessTokenBuilder().withValidUntil(expiredInFiveMinutes).build();

        // if a access token is about to expire than we assume its expired, so we force a refresh
        assertThat(accessToken.isNotExpired(), is(true));
    }

    @Test(expected = JSONException.class)
    public void testAccessToken_MalformedJson() {
        new AccessToken(prepareMalformedJSONObject());
    }

    @Test(expected = Test.None.class)
    public void constructor() {
        new AccessToken("token", prepareCalendar(), 123);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_TokenIsNull() {
        new AccessToken(null, prepareCalendar(), 123);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_TokenIsEmpty() {
        new AccessToken("", prepareCalendar(), 123);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_Should_ThrowException_When_CalendarIsNull() {
        new AccessToken("token", null, 123);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_Should_ThrowException_When_DurationInMillisIsZero() {
        new AccessToken("token", prepareCalendar(), 0);
    }


    private Calendar prepareCalendar() {
        return CalendarUtils.stringToCalendar("2016-07-28T12:58:50.205+0200");
    }

    private JSONObject prepareMalformedJSONObject() {
        JSONObject accessToken = new AccessTokenBuilder().buildJsonObject();
        accessToken.remove("durationInMillis");
        accessToken.put("urationInMillis", 123);
        return accessToken;
    }

}
