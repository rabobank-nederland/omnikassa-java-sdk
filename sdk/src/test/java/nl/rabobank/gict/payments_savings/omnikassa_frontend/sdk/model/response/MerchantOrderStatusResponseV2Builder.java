package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response;

import org.json.JSONObject;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.TestBuilder;

public class MerchantOrderStatusResponseV2Builder implements TestBuilder<MerchantOrderStatusResponseV2> {
    private boolean moreOrderResultsAvailable = false;
    private String orderResults = initializeOrderResults();
    private String signature = "eadd65e96b347c006e8e4e95cec30b9ab988226a7bb1cbdc9da9a521f245dd553cdb665f9cd5677fe782af3c0efee3a30025084b999a0bca795ce45eec23a2e7";

    private static String initializeOrderResults() {
        return "[ " +
               "{ poiId:'1', totalAmount:{ amount:'599', currency:'EUR'}, errorCode:'', paidAmount:{ amount:'0', currency:'EUR'}, merchantOrderId:'MYSHOP0001', orderStatusDateTime:'2016-07-28T12:51:15.574+02:00', orderStatus:'CANCELLED', omnikassaOrderId:'aec58605-edcf-4886-b12d-594a8a8eea60', transactions:[{" +
               "id:'1', paymentBrand:'IDEAL', type:PAYMENT, status:SUCCESS, amount:{ amount:'599', currency:'EUR'}, confirmedAmount:{ amount:'599', currency:'EUR'}, startTime:'2016-07-28T12:51:15.574+02:00', lastUpdateTime:'2016-07-28T12:51:15.574+02:00'}]}, " +
               "{ poiId:'1', totalAmount:{ amount:'599', currency:'EUR'}, errorCode:'', paidAmount:{ amount:'599', currency:'EUR'}, merchantOrderId:'MYSHOP0002', orderStatusDateTime:'2016-07-28T13:58:50.205+02:00', orderStatus:'COMPLETED', omnikassaOrderId:'e516e630-9713-4cfa-ae88-c5fbc4b06744', transactions:[{" +
               "id:'1', paymentBrand:'IDEAL', type:PAYMENT, status:SUCCESS, amount:{ amount:'599', currency:'EUR'}, confirmedAmount:{ amount:'599', currency:'EUR'}, startTime:'2016-07-28T12:51:15.574+02:00', lastUpdateTime:'2016-07-28T12:51:15.574+02:00'}]}" +
               " ]";
    }

    public MerchantOrderStatusResponseV2Builder withMoreOrderResultsAvailable(boolean moreOrderResultsAvailable) {
        this.moreOrderResultsAvailable = moreOrderResultsAvailable;
        return this;
    }

    public MerchantOrderStatusResponseV2Builder withOrderResults(String orderResults) {
        this.orderResults = orderResults;
        return this;
    }

    public MerchantOrderStatusResponseV2Builder withSignature(String signature) {
        this.signature = signature;
        return this;
    }

    @Override
    public MerchantOrderStatusResponseV2 build() {
        return new MerchantOrderStatusResponseV2(buildJsonObject());
    }

    @Override
    public JSONObject buildJsonObject() {
        String builder = "{ orderResults:" + orderResults + ", " +
                         "signature:'" + signature + "', " +
                         "moreOrderResultsAvailable:" + moreOrderResultsAvailable + "}";
        return new JSONObject(builder);
    }
}
