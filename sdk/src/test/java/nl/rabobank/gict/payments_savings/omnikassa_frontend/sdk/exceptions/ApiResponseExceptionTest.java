package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions;

import kong.unirest.json.JSONObject;
import org.junit.Test;

import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions.ApiResponseException.ERROR_CODE_FIELD_NAME;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions.InvalidAccessTokenException.INVALID_AUTHORIZATION_ERROR_CODE;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ApiResponseExceptionTest {

    @Test
    public void invalidAccessTokenExceptionWithCustomErrorMessage() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(ERROR_CODE_FIELD_NAME, INVALID_AUTHORIZATION_ERROR_CODE);
        jsonObject.put("errorMessage", "my custom error message");

        ApiResponseException actual = ApiResponseException.of(jsonObject);

        assertThat(actual, instanceOf(InvalidAccessTokenException.class));
        assertThat(actual.getErrorCode(), is(INVALID_AUTHORIZATION_ERROR_CODE));
        assertThat(actual.getErrorMessage(), is("my custom error message"));
    }

    @Test
    public void apiResponseExceptionWithErrorMessageTitleAndStatus() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(ERROR_CODE_FIELD_NAME, 5500);
        jsonObject.put("errorMessage", "my custom error message");
        jsonObject.put("status", 401);
        jsonObject.put("title", "My custom title");

        ApiResponseException actual = ApiResponseException.of(jsonObject);

        assertThat(actual, instanceOf(ApiResponseException.class));
        assertThat(actual.getErrorCode(), is(5500));
        assertThat(actual.getErrorMessage(), is("my custom error message"));
        assertThat(actual.getTitle(), is("My custom title"));
        assertThat(actual.getStatus(), is(401));
    }
}