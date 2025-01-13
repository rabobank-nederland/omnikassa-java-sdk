package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions;

import kong.unirest.json.JSONObject;

import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions.InvalidAccessTokenException.INVALID_AUTHORIZATION_ERROR_CODE;

public class ApiResponseException extends RabobankSdkException {
    public static final String ERROR_CODE_FIELD_NAME = "errorCode";
    private static final String ERROR_MESSAGE_FIELD_NAME = "errorMessage";
    private static final String API_RETURN_MESSAGE = "The Rabobank API returned message: ";

    private final int errorCode;
    private final String errorMessage;
    private final String title;
    private final Integer status;

    ApiResponseException(int errorCode, String errorMessage) {
        super(API_RETURN_MESSAGE + errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.title = null;
        this.status = null;
    }

    ApiResponseException(int errorCode) {
        super(API_RETURN_MESSAGE + " #" + errorCode);
        this.errorCode = errorCode;
        this.errorMessage = null;
        this.title = null;
        this.status = null;
    }

    ApiResponseException(int errorCode, String errorMessage, String title, int status) {
        super(API_RETURN_MESSAGE + errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.title = title;
        this.status = status;
    }

    public static ApiResponseException of(JSONObject jsonObject) {
        int errorCode = jsonObject.getInt(ERROR_CODE_FIELD_NAME);

        if (errorCode == INVALID_AUTHORIZATION_ERROR_CODE) {
            return getInvalidAccessTokenException(jsonObject);
        }

        if (jsonObject.has("consumerMessage")) {
            String consumerMessage = jsonObject.getString("consumerMessage");
            return new ApiResponseException(errorCode, consumerMessage);
        }

        if (jsonObject.has(ERROR_MESSAGE_FIELD_NAME) && jsonObject.has("title") && jsonObject.has("status")) {
            return new ApiResponseException(errorCode,
                                            jsonObject.getString(ERROR_MESSAGE_FIELD_NAME),
                                            jsonObject.getString("title"),
                                            jsonObject.getInt("status"));
        }
        return new ApiResponseException(errorCode);
    }

    private static ApiResponseException getInvalidAccessTokenException(JSONObject jsonObject) {
        if (jsonObject.has(ERROR_MESSAGE_FIELD_NAME)) {
            String errorMessage = jsonObject.getString(ERROR_MESSAGE_FIELD_NAME);
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

    public String getTitle() { return title;}

    public Integer getStatus() { return status; }
}
