package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.connector;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.AccessToken;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.AccessTokenBuilder;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.utils.CalendarUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Calendar;
import java.util.Map;

import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.connector.TokenProvider.FieldName.ACCESS_TOKEN;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.connector.TokenProvider.FieldName.ACCESS_TOKEN_DURATION;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.connector.TokenProvider.FieldName.ACCESS_TOKEN_VALID_UNTIL;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.connector.TokenProvider.FieldName.REFRESH_TOKEN;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TokenProviderTest {
    private static final String PAST_VALID_UNTIL = "2016-09-22T10:10:04.848+0200";
    private static final String FUTURE_VALID_UNTIL = initializeFutureDate();

    private static String initializeFutureDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        return CalendarUtils.calendarToString(calendar);
    }

    @Mock
    private Map<TokenProvider.FieldName, String> map;

    private TokenProvider classUnderTest;

    @Before
    public void setUp() throws Exception {
        classUnderTest = new TokenProviderSpy(map);
    }

    @Test
    public void getRefreshToken() throws Exception {
        when(map.get(REFRESH_TOKEN)).thenReturn("refreshToken");

        assertThat(classUnderTest.getRefreshToken(), is("refreshToken"));

        verify(map).get(REFRESH_TOKEN);
    }

    @Test
    public void setAccessToken() throws Exception {
        AccessToken accessToken = new AccessTokenBuilder().withValidUntilTomorrow().build();

        String validUntilString = CalendarUtils.calendarToString(accessToken.getValidUntil());

        classUnderTest.setAccessToken(accessToken);

        verify(map).put(ACCESS_TOKEN, "token");
        verify(map).put(ACCESS_TOKEN_VALID_UNTIL, validUntilString);
        verify(map).put(ACCESS_TOKEN_DURATION, "28800000");
    }

    @Test
    public void setAndGetAccessToken() throws Exception {
        AccessToken accessToken = new AccessTokenBuilder().withValidUntilTomorrow().build();

        classUnderTest.setAccessToken(accessToken);

        // token is valid and cache is used
        assertThat(classUnderTest.getAccessToken(), is("token"));
        assertThat(classUnderTest.hasNoValidAccessToken(), is(false));
    }


    @Test
    public void setAndGetAccessToken_Expired() throws Exception {
        AccessToken accessToken = new AccessTokenBuilder().build();

        classUnderTest.setAccessToken(accessToken);

        // token is not valid, cache is not used
        assertThat(classUnderTest.getAccessToken(), nullValue(String.class));
        assertThat(classUnderTest.hasNoValidAccessToken(), is(true));
    }

    @Test
    public void getAccessToken() throws Exception {
        prepareForFetchingFromTokenProvider("token", FUTURE_VALID_UNTIL, "28800000");

        // token which is stored, is valid
        assertThat(classUnderTest.getAccessToken(), is("token"));
        assertThat(classUnderTest.hasNoValidAccessToken(), is(false));
    }

    @Test
    public void getAccessToken_WithExpiredToken() throws Exception {
        prepareForFetchingFromTokenProvider("token", PAST_VALID_UNTIL, "28800000");

        assertThat(classUnderTest.getAccessToken(), nullValue(String.class));
        assertThat(classUnderTest.hasNoValidAccessToken(), is(true));
    }

    @Test
    public void getAccessToken_WithMissingField() throws Exception {
        prepareForFetchingFromTokenProvider("token", PAST_VALID_UNTIL, "28800000");

        assertThat(classUnderTest.getAccessToken(), nullValue(String.class));
        assertThat(classUnderTest.hasNoValidAccessToken(), is(true));
    }

    private void prepareForFetchingFromTokenProvider(String token, String validUntil, String duration) {
        when(map.get(ACCESS_TOKEN)).thenReturn(token);
        when(map.get(ACCESS_TOKEN_VALID_UNTIL)).thenReturn(validUntil);
        when(map.get(ACCESS_TOKEN_DURATION)).thenReturn(duration);
    }

}