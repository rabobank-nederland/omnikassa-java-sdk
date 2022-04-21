package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response;

import kong.unirest.json.JSONObject;

import java.util.Arrays;
import java.util.List;

/**
 * This class is what the Rabobank api transmits when the customers webhook is called.
 * It indicates that it is now OK to call the endpoint.retrieveAnnouncement( "this apiNotification" ) function.
 */
public final class ApiNotification extends SignedResponse {
    private int poiId;
    private String authentication;
    private String expiry;
    private String eventName;

    public ApiNotification() {
        super((String) null);
    }

    /**
     * Construct an ApiNotification from the individual fields
     *
     * @param poiId          the poi id for which this Notification is meant (only relevant of multiple poi share one webhook)
     * @param authentication an authentication string which the SDK uses to retrieve the status updates from the API
     * @param expiry         a date string till when this Notification can be used to retrieve data from the remote API
     * @param eventName      the type of event which is retrieved
     * @param signature      the signature of this notification, this is validated by the SDK when retrieving the details
     */
    public ApiNotification(int poiId, String authentication, String expiry, String eventName, String signature) {
        super(signature);
        this.poiId = poiId;
        this.authentication = authentication;
        this.expiry = expiry;
        this.eventName = eventName;
    }

    public ApiNotification(JSONObject jsonObject) {
        super(jsonObject);
        this.poiId = jsonObject.getInt("poiId");
        this.authentication = jsonObject.getString("authentication");
        this.expiry = jsonObject.getString("expiry");
        this.eventName = jsonObject.getString("eventName");
    }

    /**
     * @return the poi id for which this Notification is meant (only relevant of multiple poi share one webhook)
     */
    public int getPoiId() {
        return poiId;
    }

    /**
     * @return an authentication string which the SDK uses to retrieve the status updates from the API
     */
    public String getAuthentication() {
        return authentication;
    }


    /**
     * @return a date string till when this Notification can be used to retrieve data from the remote API
     */
    public String getExpiry() {
        return expiry;
    }

    /**
     * @return the type of event which is retrieved
     */
    public String getEventName() {
        return eventName;
    }

    @Override
    public List<String> getSignatureData() {
        return Arrays.asList(
                authentication,
                expiry,
                eventName,
                String.valueOf(poiId)
        );
    }

}
