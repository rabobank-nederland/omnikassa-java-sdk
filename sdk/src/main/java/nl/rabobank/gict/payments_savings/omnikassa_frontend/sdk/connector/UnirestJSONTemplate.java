package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.connector;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.body.RequestBodyEntity;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.JsonConvertible;
import org.json.JSONObject;

class UnirestJSONTemplate {
    private final String baseURL;

    UnirestJSONTemplate(String baseURL) {
        this.baseURL = baseURL;
    }

    public JSONObject get(String path, String token) throws UnirestException {
        GetRequest jsonResponse = Unirest.get(baseURL + "/" + path)
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + token);

        return jsonResponse.asJson().getBody().getObject();
    }

    public JSONObject post(String path, JsonConvertible body, String token) throws UnirestException {
        RequestBodyEntity requestBodyEntity = Unirest.post(baseURL + "/" + path)
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(body.asJson());

        return requestBodyEntity.asJson().getBody().getObject();
    }

    public JSONObject postWithHeader(String path, JsonConvertible body, String headerName, String headerValue,  String token) throws UnirestException {
        RequestBodyEntity requestBodyEntity = Unirest.post(baseURL + "/" + path)
                                                     .header("Content-type", "application/json")
                                                     .header("Authorization", "Bearer " + token)
                                                     .header(headerName, headerValue)
                                                     .body(body.asJson());

        return requestBodyEntity.asJson().getBody().getObject();
    }
}
