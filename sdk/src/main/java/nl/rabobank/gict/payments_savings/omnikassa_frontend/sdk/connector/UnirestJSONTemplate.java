package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.connector;

import kong.unirest.GetRequest;
import kong.unirest.HttpMethod;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.RequestBodyEntity;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions.RabobankSdkException;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.JsonConvertible;

import java.util.Map;


class UnirestJSONTemplate {
    private final String baseURL;

    UnirestJSONTemplate(String baseURL) {
        this.baseURL = baseURL;
    }

    public JSONObject getWithHeader(String path, String token) {
        GetRequest jsonResponse = Unirest.get(baseURL + "/" + path)
                                         .header("Content-type", "application/json")
                                         .header("Authorization", "Bearer " + token);

        return jsonResponse.asJson().getBody().getObject();
    }

    private static JSONObject getJSONObject(HttpResponse<JsonNode> json, HttpMethod httpMethod, String path) throws RabobankSdkException {
        if (json.getParsingError().isPresent()) {
            throw new RabobankSdkException("Error parsing JSON response for request: " + httpMethod + ' ' + path);
        }
        return json.getBody().getObject();
    }

    public JSONObject postWithHeader(String path, JsonConvertible body, Map<String, String> headers, String token) {
        RequestBodyEntity requestBodyEntity = Unirest.post(baseURL + "/" + path)
                                                     .header("Content-type", "application/json")
                                                     .header("Authorization", "Bearer " + token)
                                                     .body(body.asJson());

        return requestBodyEntity.asJson().getBody().getObject();
    }

    public JSONObject getOrderStatus(String path, String token) throws RabobankSdkException {
        GetRequest getRequest = Unirest.get(baseURL + "/" + path)
                                       .header("Content-type", "application/json")
                                       .header("Authorization", "Bearer " + token);

        return getRequest.asJson().getBody().getObject();
    }

    public JSONObject getShopperPaymentDetails(String path, String shopperRef, String token) throws
                                                                                            RabobankSdkException {
        GetRequest jsonResponse = Unirest.get( baseURL + "/" + path)
                                         .queryString("shopper-ref", shopperRef)
                                         .header("Content-type", "application/json")
                                         .header("Authorization", "Bearer " + token);

        return getJSONObject(jsonResponse.asJson(), jsonResponse.getHttpMethod(), jsonResponse.getUrl());
    }

    public int deleteShopperPaymentDetails(String path, String shopperRef, String token) {
        return Unirest.delete(baseURL + "/" + path)
                      .queryString("shopper-ref", shopperRef)
                      .header("Content-type", "application/json")
                      .header("Authorization", "Bearer " + token)
                      .asJson().getStatus();
    }

}
