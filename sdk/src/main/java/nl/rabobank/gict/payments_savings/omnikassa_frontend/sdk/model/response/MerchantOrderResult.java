package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.Money;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.utils.CalendarUtils.stringToCalendar;

/**
 * this class contains details about an order, these details can then be used to update locally stored orders.
 */
public final class MerchantOrderResult {
    private final int pointOfInteractionId;
    private final String merchantOrderId;
    private final String omnikassaOrderId;
    private final String orderStatus;
    private final String errorCode;
    private final String orderStatusDateTime;
    private final Money paidAmount;
    private final Money totalAmount;
    private final List<TransactionInfo> transactionInfo;

    MerchantOrderResult(JSONObject jsonObject) {
        this.pointOfInteractionId = jsonObject.getInt("poiId");
        this.merchantOrderId = jsonObject.getString("merchantOrderId");
        this.omnikassaOrderId = jsonObject.getString("omnikassaOrderId");
        this.orderStatus = jsonObject.getString("orderStatus");
        this.orderStatusDateTime = jsonObject.getString("orderStatusDateTime");
        this.errorCode = jsonObject.getString("errorCode");
        this.paidAmount = Money.fromJson(jsonObject.getJSONObject("paidAmount"));
        this.totalAmount = Money.fromJson(jsonObject.getJSONObject("totalAmount"));
        this.transactionInfo = StreamSupport.stream(jsonObject.getJSONArray("transactions").spliterator(), false)
                                            .map((transactionJsonObject) -> new TransactionInfo((JSONObject) transactionJsonObject))
                                            .collect(Collectors.toList());
    }


    public int getPointOfInteractionId() {
        return pointOfInteractionId;
    }

    public String getMerchantOrderId() {
        return merchantOrderId;
    }

    public String getOmnikassaOrderId() {
        return omnikassaOrderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public Calendar getOrderStatusDateTime() {
        return stringToCalendar(orderStatusDateTime);
    }

    public Money getPaidAmount() {
        return paidAmount;
    }

    public Money getTotalAmount() {
        return totalAmount;
    }

    public List<TransactionInfo> getTransactionInfo() {
        return transactionInfo;
    }

    public List<String> getSignatureData() {
        List<String> signatureData = new ArrayList<>();
        signatureData.add(merchantOrderId);
        signatureData.add(omnikassaOrderId);
        signatureData.add(String.valueOf(pointOfInteractionId));
        signatureData.add(orderStatus);
        signatureData.add(orderStatusDateTime);
        signatureData.add(errorCode);
        signatureData.add(paidAmount.getCurrency().name());
        signatureData.add(String.valueOf(paidAmount.getAmountInCents()));
        signatureData.add(totalAmount.getCurrency().name());
        signatureData.add(String.valueOf(totalAmount.getAmountInCents()));
        signatureData.addAll(transactionInfo.stream().flatMap(t -> t.getSignatureData().stream()).collect(Collectors.toList()));
        return Collections.unmodifiableList(signatureData);
    }

}
