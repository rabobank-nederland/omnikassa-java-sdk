package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.TestBuilder;
import kong.unirest.json.JSONObject;

public class MerchantOrderStatusResponseBuilder implements TestBuilder<MerchantOrderStatusResponse> {
    private boolean moreOrderResultsAvailable = false;
    private String orderResults = initializeOrderResults();
    private String signature = "7a3ff0c6a749fc7391260d86f132443e8590be6849e0ae5b6713f0c531a909dda180c2ccada77313016c2f50b65793737f10c7e8d5ab58313817951dfb71adb8";

    private static String initializeOrderResults() {
        return "[ " +
               "{ poiId:'1', totalAmount:{ amount:'599', currency:'EUR'}, errorCode:'', paidAmount:{ amount:'0', currency:'EUR'}, merchantOrderId:'MYSHOP0001', orderStatusDateTime:'2016-07-28T12:51:15.574+02:00', orderStatus:'FAILURE', omnikassaOrderId:'aec58605-edcf-4886-b12d-594a8a8eea60', transactions:[{" +
               "id:'1', paymentBrand:'IDEAL', type:PAYMENT, status:SUCCESS, amount:{ amount:'599', currency:'EUR'}, confirmedAmount:{ amount:'599', currency:'EUR'}, startTime:'2016-07-28T12:51:15.574+02:00', lastUpdateTime:'2016-07-28T12:51:15.574+02:00'}]}, " +
               "{ poiId:'1', totalAmount:{ amount:'599', currency:'EUR'}, errorCode:'', paidAmount:{ amount:'599', currency:'EUR'}, merchantOrderId:'MYSHOP0002', orderStatusDateTime:'2016-07-28T13:58:50.205+02:00', orderStatus:'COMPLETED', omnikassaOrderId:'e516e630-9713-4cfa-ae88-c5fbc4b06744', transactions:[{" +
               "id:'1', paymentBrand:'IDEAL', type:PAYMENT, status:SUCCESS, amount:{ amount:'599', currency:'EUR'}, confirmedAmount:{ amount:'599', currency:'EUR'}, startTime:'2016-07-28T12:51:15.574+02:00', lastUpdateTime:'2016-07-28T12:51:15.574+02:00'}]}" +
               " ]";
    }

    public MerchantOrderStatusResponseBuilder withMoreOrderResultsAvailable(boolean moreOrderResultsAvailable) {
        this.moreOrderResultsAvailable = moreOrderResultsAvailable;
        return this;
    }

    public MerchantOrderStatusResponseBuilder withOrderResults(String orderResults) {
        this.orderResults = orderResults;
        return this;
    }

    public MerchantOrderStatusResponseBuilder withSignature(String signature) {
        this.signature = signature;
        return this;
    }

    @Override
    public MerchantOrderStatusResponse build() {
        return new MerchantOrderStatusResponse(buildJsonObject());
    }

    @Override
    public JSONObject buildJsonObject() {
        String builder = "{ orderResults:" + orderResults + ", " +
                         "signature:'" + signature + "', " +
                         "moreOrderResultsAvailable:" + moreOrderResultsAvailable + "}";
        return new JSONObject(builder);
    }
}
