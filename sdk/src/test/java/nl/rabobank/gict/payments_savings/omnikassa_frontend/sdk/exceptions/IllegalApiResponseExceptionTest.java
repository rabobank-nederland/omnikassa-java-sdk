package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions;

import kong.unirest.json.JSONObject;
import org.junit.Test;

import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions.IllegalApiResponseException.ERROR_CODE_FIELD_NAME;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions.InvalidAccessTokenException.INVALID_AUTHORIZATION_ERROR_CODE;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class IllegalApiResponseExceptionTest {

    @Test
    public void invalidAccessTokenExceptionWithCustomErrorMessage() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(ERROR_CODE_FIELD_NAME, INVALID_AUTHORIZATION_ERROR_CODE);
        jsonObject.put("errorMessage", "my custom error message");

        IllegalApiResponseException actual = IllegalApiResponseException.of(jsonObject);

        assertThat(actual, instanceOf(InvalidAccessTokenException.class));
        assertThat(actual.getErrorCode(), is(INVALID_AUTHORIZATION_ERROR_CODE));
        assertThat(actual.getErrorMessage(), is("my custom error message"));
    }
}