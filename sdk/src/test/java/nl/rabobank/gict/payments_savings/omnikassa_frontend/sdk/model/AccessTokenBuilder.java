package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model;

import org.json.JSONObject;

import java.util.Calendar;

import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.utils.CalendarUtils.calendarToString;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.utils.CalendarUtils.stringToCalendar;

public class AccessTokenBuilder implements TestBuilder<AccessToken> {
    private String token = "token";
    private Calendar validUntil = stringToCalendar("2016-09-22T10:10:04.848+0200");
    private int durationInMillis = 1000 * 60 * 60 * 8;


    public AccessTokenBuilder withToken(String token) {
        this.token = token;
        return this;
    }

    public AccessTokenBuilder withValidUntil(Calendar validUntil) {
        this.validUntil = validUntil;
        return this;
    }

    public AccessTokenBuilder withValidUntilTomorrow() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        return withValidUntil(calendar);
    }

    public AccessTokenBuilder withValidUntilNow() {
        return withValidUntil(Calendar.getInstance());
    }

    public AccessTokenBuilder withDurationInMillis(int durationInMillis) {
        this.durationInMillis = durationInMillis;
        return this;
    }

    @Override
    public AccessToken build() {
        return new AccessToken(buildJsonObject());
    }

    @Override
    public JSONObject buildJsonObject() {
        JSONObject response = new JSONObject();
        response.put("token", token);
        response.put("validUntil", calendarToString(validUntil));
        response.put("durationInMillis", durationInMillis);
        return response;
    }
}
