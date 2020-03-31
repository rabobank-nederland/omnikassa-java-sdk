package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.Currency;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MerchantOrderResultTest {

    @Test
    public void getSignatureData_Should_ReturnCorrectSignatureData() {
        String expectedSignatureData = "SHOP1,ORDER1,1,COMPLETED,2000-01-01T00:00:00.000-0200,NONE,EUR,100,EUR,100";
        String actualSignatureData = StringUtils.join(getMerchantOrderResult().getSignatureData(), ",");
        assertEquals(expectedSignatureData, actualSignatureData);
    }

    private MerchantOrderResult getMerchantOrderResult() {
        JSONObject object = new JSONObject();
        object.put("poiId", 1);
        object.put("merchantOrderId", "SHOP1");
        object.put("omnikassaOrderId", "ORDER1");
        object.put("orderStatus", "COMPLETED");
        object.put("errorCode", "NONE");
        object.put("orderStatusDateTime", "2000-01-01T00:00:00.000-0200");
        object.put("paidAmount", getJsonMoney(Currency.EUR, 100));
        object.put("totalAmount", getJsonMoney(Currency.EUR, 100));
        return new MerchantOrderResult(object);
    }

    private JSONObject getJsonMoney(Currency currency, long amountInCents) {
        JSONObject object = new JSONObject();
        object.put("amount", String.valueOf(amountInCents));
        object.put("currency", currency);
        return object;
    }

}
