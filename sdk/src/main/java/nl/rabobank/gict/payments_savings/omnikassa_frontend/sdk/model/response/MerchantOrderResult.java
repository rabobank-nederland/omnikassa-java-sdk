package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.Money;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

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

    MerchantOrderResult(JSONObject jsonObject) {
        this.pointOfInteractionId = jsonObject.getInt("poiId");
        this.merchantOrderId = jsonObject.getString("merchantOrderId");
        this.omnikassaOrderId = jsonObject.getString("omnikassaOrderId");
        this.orderStatus = jsonObject.getString("orderStatus");
        this.orderStatusDateTime = jsonObject.getString("orderStatusDateTime");
        this.errorCode = jsonObject.getString("errorCode");
        this.paidAmount = Money.fromJson(jsonObject.getJSONObject("paidAmount"));
        this.totalAmount = Money.fromJson(jsonObject.getJSONObject("totalAmount"));
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

    public List<String> getSignatureData() {
        return Arrays.asList(
                merchantOrderId,
                omnikassaOrderId,
                String.valueOf(pointOfInteractionId),
                orderStatus,
                orderStatusDateTime,
                errorCode,
                paidAmount.getCurrency().name(),
                String.valueOf(paidAmount.getAmountInCents()),
                totalAmount.getCurrency().name(),
                String.valueOf(totalAmount.getAmountInCents()));
    }

}
