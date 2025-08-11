package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.request;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.IdealFastCheckoutOrder;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.JsonConvertible;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.MerchantOrder;

public class OrderRequestFactory {

    public JsonConvertible retrieveCorrectRequest(Object orderRequest){
        if (orderRequest instanceof MerchantOrder){
            return new MerchantOrderRequest((MerchantOrder) orderRequest);
        }
        if (orderRequest instanceof IdealFastCheckoutOrder){
            return new IdealFastCheckoutMerchantOrderRequest((IdealFastCheckoutOrder) orderRequest);
        }
        return null;
    }

}
