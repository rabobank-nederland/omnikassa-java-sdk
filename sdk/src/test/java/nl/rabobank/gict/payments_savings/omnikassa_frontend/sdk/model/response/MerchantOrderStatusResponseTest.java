package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response;

import org.apache.commons.lang3.StringUtils;
import kong.unirest.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MerchantOrderStatusResponseTest {
    @Test
    public void getSignatureData_Should_ReturnCorrectSignatureData() {
        MerchantOrderStatusResponse merchantOrderStatusResponse = new MerchantOrderStatusResponse(new JSONObject("{'orderResults':[{'poiId':'201','totalAmount':{'amount':'100','currency':'EUR'},'errorCode':'NONE','paidAmount':{'amount':'100','currency':'EUR'},'merchantOrderId':'8','orderStatusDateTime':'2016-08-26T13:04:20.304+02:00','orderStatus':'COMPLETED','omnikassaOrderId':'2dbef5cd-f009-461b-b968-57d87000a5b1', transactions:[]}],'signature':'e2051763e4efd43438f70a9df94b161b9c7253919e12ea64983e8789a13b3d5c','moreOrderResultsAvailable':false}"));
        String expectedSignatureData = "false,8,2dbef5cd-f009-461b-b968-57d87000a5b1,201,COMPLETED,2016-08-26T13:04:20.304+02:00,NONE,EUR,100,EUR,100";
        String actualSignatureData = StringUtils.join(merchantOrderStatusResponse.getSignatureData(), ",");
        assertEquals(expectedSignatureData, actualSignatureData);
    }
}
