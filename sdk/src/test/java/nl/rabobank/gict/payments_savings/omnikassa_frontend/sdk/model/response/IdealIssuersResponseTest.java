package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response;


import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.JsonFileConverter;
import org.json.JSONObject;
import org.junit.Test;

import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.JSONObjectCreator.createJSONObjectForIdealIssuer;
import static org.junit.Assert.assertEquals;

public class IdealIssuersResponseTest {
    private final JsonFileConverter converter = new JsonFileConverter();

    @Test
    public void asJson_Should_ReturnCorrectJsonObjectEmpty() {
        // Arrange
        IdealIssuersResponse idealIssuersResponse = new IdealIssuersResponse(createJSONObjectForIdealIssuer());
        JSONObject expectedJSONObject = converter.convert("issuers_response_single.json");
        IdealIssuersResponse expectedIdealIssuersResponse = new IdealIssuersResponse(expectedJSONObject);

        // Assert
        assertEquals(idealIssuersResponse, expectedIdealIssuersResponse);
    }
}
