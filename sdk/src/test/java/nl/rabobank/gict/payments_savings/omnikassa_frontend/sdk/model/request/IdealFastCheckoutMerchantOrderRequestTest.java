package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.request;

import kong.unirest.json.JSONObject;
import org.joda.time.DateTimeUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.IdealFastCheckoutOrderTestFactory;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.JsonFileConverter;

import static org.joda.time.DateTime.parse;
import static org.joda.time.DateTimeUtils.setCurrentMillisFixed;
import static org.junit.Assert.assertEquals;

public class IdealFastCheckoutMerchantOrderRequestTest {

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
    public void asJson_Should_ReturnCorrectJsonObject_Full() {
        IdealFastCheckoutMerchantOrderRequest idealFastCheckoutMerchantOrderRequest = createNewIdealFastCheckoutMerchantOrderRequest();
        JSONObject expectedJSONObject = converter.convert("ideal_fast_checkout_order_request_full.json");
        JSONObject actualJSONObject = idealFastCheckoutMerchantOrderRequest.asJson();
        assertEquals(expectedJSONObject, actualJSONObject);
    }

    private IdealFastCheckoutMerchantOrderRequest createNewIdealFastCheckoutMerchantOrderRequest() {
        return new IdealFastCheckoutMerchantOrderRequest(IdealFastCheckoutOrderTestFactory.any());
    }
}
