package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.request;

import org.joda.time.DateTimeUtils;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.JsonFileConverter;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.MerchantOrderTestFactory;

import static org.joda.time.DateTime.parse;
import static org.joda.time.DateTimeUtils.setCurrentMillisFixed;

public class MerchantOrderRequestTest {

    private JsonFileConverter converter = new JsonFileConverter();

    @Before
    public void setup() {
        setCurrentMillisFixed(parse("2017-08-07T16:28:51.504+02:00").getMillis());
    }

    @After
    public void tearDown() {
        DateTimeUtils.setCurrentMillisSystem();
    }

    @Test
    public void asJson_Should_ReturnCorrectJsonObject() {
        MerchantOrderRequest merchantOrderRequest = createSimpleMerchantOrderRequest();
        JSONObject expectedJSONObject = converter.convert("merchant_order_request_simple.json");
        JSONObject actualJSONObject = merchantOrderRequest.asJson();
        JSONAssert.assertEquals(actualJSONObject, expectedJSONObject, true);
    }

    @Test
    public void asJson_Should_ReturnCorrectJsonObject_Full() {
        MerchantOrderRequest merchantOrderRequest = createNewMerchantOrderRequest();
        JSONObject expectedJSONObject = converter.convert("merchant_order_request_full.json");
        JSONObject actualJSONObject = merchantOrderRequest.asJson();
        JSONAssert.assertEquals(expectedJSONObject, actualJSONObject, true);
    }

    private static MerchantOrderRequest createSimpleMerchantOrderRequest() {
        return new MerchantOrderRequest(MerchantOrderTestFactory.any());
    }

    private static MerchantOrderRequest createNewMerchantOrderRequest() {
        return new MerchantOrderRequest(MerchantOrderTestFactory.includingOptionalFields());
    }
}