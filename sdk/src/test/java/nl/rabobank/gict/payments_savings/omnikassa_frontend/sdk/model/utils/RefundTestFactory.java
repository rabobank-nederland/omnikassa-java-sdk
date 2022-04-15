package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.utils;

import org.json.JSONObject;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.Money;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.Currency;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.VatCategory;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.request.InitiateRefundRequest;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.RefundDetailsResponse;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.TransactionRefundableDetailsResponse;

import java.math.BigDecimal;

public class RefundTestFactory {

    public static RefundDetailsResponse defaultRefundDetailsResponse() {
        return new RefundDetailsResponse(defaultRefundDetailsResponseJsonObject());
    }

    public static TransactionRefundableDetailsResponse defaultTransactionRefundableDetailsResponse() {
        return new TransactionRefundableDetailsResponse(defaultTransactionRefundableDetailsResponseJsonObject());
    }

    public static InitiateRefundRequest defaultInitiateRefundRequest() {
        return new InitiateRefundRequest(Money.fromEuros(Currency.EUR, new BigDecimal(100)), "description", VatCategory.HIGH);
    }

    public static JSONObject defaultRefundDetailsResponseJsonObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("refundId", "25da863a-60a5-475d-ae47-c0e4bd1bec31");
        jsonObject.put("refundTransactionId", "25da863a-60a5-475d-ae47-c0e4bd1bec32");
        jsonObject.put("createdAt", "2000-01-01T00:00:00.000-0200");
        jsonObject.put("updatedAt", "2000-01-01T00:00:00.000-0200");
        jsonObject.put("refundMoney", Money.fromEuros(Currency.EUR, new BigDecimal(100)).asJson());
        jsonObject.put("vatCategory", "1");
        jsonObject.put("paymentBrand", "IDEAL");
        jsonObject.put("status", "COMPLETED");
        jsonObject.put("description", "test description");
        jsonObject.put("transactionId", "25da863a-60a5-475d-ae47-c0e4bd1bec33");
        return jsonObject;
    }

    public static JSONObject defaultTransactionRefundableDetailsResponseJsonObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("transactionId", "25da863a-60a5-475d-ae47-c0e4bd1bec31");
        jsonObject.put("refundableMoney", Money.fromEuros(Currency.EUR, new BigDecimal(100)).asJson());
        jsonObject.put("expiryDatetime", "2000-01-01T00:00:00.000-0200");
        return jsonObject;
    }
}
