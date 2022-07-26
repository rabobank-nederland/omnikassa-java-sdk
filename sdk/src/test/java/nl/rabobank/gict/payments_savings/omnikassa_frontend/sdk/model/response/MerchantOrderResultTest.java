package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.Currency;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.PaymentBrand;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.TransactionStatus;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.TransactionType;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class MerchantOrderResultTest {

    @Test
    public void getSignatureData_Should_ReturnCorrectSignatureData() {
        String expectedSignatureData = "SHOP1,ORDER1,1,COMPLETED,2000-01-01T00:00:00.000-0200,NONE,EUR,100,EUR,100," +
                                       "1,IDEAL,PAYMENT,SUCCESS,EUR,100,EUR,100,2016-07-28T12:51:15.574+02:00,2016-07-28T12:51:15.574+02:00," +
                                       "2,IDEAL,PAYMENT,SUCCESS,EUR,200,EUR,200,2016-07-28T12:51:15.574+02:00,2016-07-28T12:51:15.574+02:00," +
                                       "3,IDEAL,PAYMENT,SUCCESS,EUR,300,,,2016-07-28T12:51:15.574+02:00,2016-07-28T12:51:15.574+02:00";
        String actualSignatureData = StringUtils.join(getMerchantOrderResult().getSignatureData(), ",");
        assertEquals(expectedSignatureData, actualSignatureData);
    }

    @Test
    public void getSignatureDataWithoutTransactions_Should_ReturnCorrectSignatureData() {
        String expectedSignatureData = "SHOP1,ORDER1,1,COMPLETED,2000-01-01T00:00:00.000-0200,NONE,EUR,100,EUR,100";
        String actualSignatureData = StringUtils.join(getMerchantOrderResultWithoutTransactions().getSignatureData(), ",");
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

        JSONObject firstTransaction = getTransactionObject("1", 100L, true);
        JSONObject secondTransaction = getTransactionObject("2", 200L, true);
        JSONObject thirdTransaction = getTransactionObject("3", 300L, false);
        object.put("transactions", new JSONArray(Arrays.asList(firstTransaction, secondTransaction, thirdTransaction)));
        return new MerchantOrderResult(object);
    }

    private MerchantOrderResult getMerchantOrderResultWithoutTransactions() {
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

    private JSONObject getTransactionObject(String id, Long amount, boolean withConfirmedAmount) {
        JSONObject transactionObject = new JSONObject();
        transactionObject.put("id", id);
        transactionObject.put("paymentBrand", PaymentBrand.IDEAL);
        transactionObject.put("type", TransactionType.PAYMENT);
        transactionObject.put("status", TransactionStatus.SUCCESS);
        transactionObject.put("amount", getJsonMoney(Currency.EUR, amount));
        if (withConfirmedAmount) {
            transactionObject.put("confirmedAmount", getJsonMoney(Currency.EUR, amount));
        }
        transactionObject.put("startTime", "2016-07-28T12:51:15.574+02:00");
        transactionObject.put("lastUpdateTime", "2016-07-28T12:51:15.574+02:00");
        return transactionObject;
    }

}
