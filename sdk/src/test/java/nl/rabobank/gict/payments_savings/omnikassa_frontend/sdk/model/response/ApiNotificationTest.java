package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.junit.Test;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions.IllegalSignatureException;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions.RabobankSdkException;

import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class ApiNotificationTest {
    private static final byte[] SIGNING_KEY = "secret".getBytes(UTF_8);

    @Test
    public void testBuildFromJson() throws Exception {
        JSONObject json = new ApiNotificationBuilder().buildJsonObject();

        ApiNotification apiNotification = new ApiNotification(json);

        assertApiNotification(apiNotification);
    }

    @Test
    public void testBuildFromValues() throws Exception {
        ApiNotification apiNotification = new ApiNotificationBuilder().build();

        assertApiNotification(apiNotification);
    }

    private void assertApiNotification(ApiNotification apiNotification) throws RabobankSdkException {
        assertThat(apiNotification.getPoiId(), is(1));
        assertThat(apiNotification.getAuthentication(), is("authentication"));
        assertThat(apiNotification.getExpiry(), is("2000-01-01T00:00:00.000-0200"));
        assertThat(apiNotification.getEventName(), is("event"));

        apiNotification.validateSignature(SIGNING_KEY);
    }

    @Test(expected = IllegalSignatureException.class)
    public void testBuildFromJson_InvalidSignature() throws Exception {
        JSONObject json = new ApiNotificationBuilder().withSignature("wrong").buildJsonObject();

        ApiNotification apiNotification = new ApiNotification(json);

        apiNotification.validateSignature(SIGNING_KEY);
    }

    @Test
    public void getSignatureData_Should_ReturnCorrectSignatureData() {
        String expectedSignatureData = "authentication,2000-01-01T00:00:00.000-0200,event,1";
        List<String> signatureData = new ApiNotificationBuilder().build().getSignatureData();
        String actualSignatureData = StringUtils.join(signatureData, ",");
        assertEquals(expectedSignatureData, actualSignatureData);
    }

    @Test
    public void noArgConstructor() {
        ApiNotification notification = new ApiNotification();

        assertThat(notification.getAuthentication(), nullValue());
        assertThat(notification.getEventName(), nullValue());
        assertThat(notification.getExpiry(), nullValue());
        assertThat(notification.getPoiId(), is(0));
    }
}
