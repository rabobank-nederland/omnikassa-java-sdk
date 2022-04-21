package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions;

import kong.unirest.json.JSONObject;

import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions.InvalidAccessTokenException.INVALID_AUTHORIZATION_ERROR_CODE;

public class IllegalApiResponseException extends RabobankSdkException {
    public static final String ERROR_CODE_FIELD_NAME = "errorCode";

    private final int errorCode;
    private final String errorMessage;

    IllegalApiResponseException(int errorCode, String errorMessage) {
        super("The Rabobank API returned message: " + errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    IllegalApiResponseException(int errorCode) {
        super("The Rabobank API returned errorCode: #" + errorCode);
        this.errorCode = errorCode;
        this.errorMessage = null;
    }

    public static IllegalApiResponseException of(JSONObject jsonObject) {
        int errorCode = jsonObject.getInt(ERROR_CODE_FIELD_NAME);

        if (errorCode == INVALID_AUTHORIZATION_ERROR_CODE) {
            return getInvalidAccessTokenException(jsonObject);
        }

        if (jsonObject.has("consumerMessage")) {
            String consumerMessage = jsonObject.getString("consumerMessage");
            return new IllegalApiResponseException(errorCode, consumerMessage);
        }
        return new IllegalApiResponseException(errorCode);
    }

    private static IllegalApiResponseException getInvalidAccessTokenException(JSONObject jsonObject) {
        if (jsonObject.has("errorMessage")) {
            String errorMessage = jsonObject.getString("errorMessage");
            return new InvalidAccessTokenException(errorMessage);
        }
        return new InvalidAccessTokenException();
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
