package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Response from the Rabobank API when request the list of available issuers.
 */
public class IdealIssuersResponse {
    public static final String ISSUERS_KEY = "issuers";

    /**
     * The list of issuers that is currently activated for the webshop.
     */
    private final List<Issuer> issuers;

    public IdealIssuersResponse(JSONObject jsonIssuers) {
        JSONArray issuersJSONArray = jsonIssuers.getJSONArray(ISSUERS_KEY);
        issuers = Collections.unmodifiableList(parseIssuers(issuersJSONArray));
    }

    public List<Issuer> getIssuers() {
        return issuers;
    }

    private List<Issuer> parseIssuers(JSONArray issuers) {
        if (issuers.length() == 0) {
            return Collections.emptyList();
        }

        List<Issuer> parsedIssuers = new ArrayList<>();
        for (Object issuer : issuers) {
            JSONObject jsonIssuerObject = (JSONObject) issuer;
            parsedIssuers.add(new Issuer(jsonIssuerObject));
        }

        return parsedIssuers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IdealIssuersResponse that = (IdealIssuersResponse) o;
        return issuers.equals(that.issuers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(issuers);
    }
}
