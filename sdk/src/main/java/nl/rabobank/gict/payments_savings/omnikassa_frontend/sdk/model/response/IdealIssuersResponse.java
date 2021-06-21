package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

/**
 * Response from the Rabobank API when request the list of available issuers.
 */
public final class IdealIssuersResponse {
    static final String ISSUERS_KEY = "issuers";

    /**
     * The list of issuers that is currently activated for the webshop.
     */
    private final List<IdealIssuer> idealIssuers;

    public IdealIssuersResponse(JSONObject jsonIssuers) {
        JSONArray issuersJSONArray = jsonIssuers.getJSONArray(ISSUERS_KEY);
        idealIssuers = unmodifiableList(parseIssuers(issuersJSONArray));
    }

    public List<IdealIssuer> getIssuers() {
        return idealIssuers;
    }

    private List<IdealIssuer> parseIssuers(JSONArray issuers) {
        if (issuers.length() == 0) {
            return emptyList();
        }

        List<IdealIssuer> parsedIdealIssuers = new ArrayList<>();
        for (Object issuer : issuers) {
            JSONObject jsonIssuerObject = (JSONObject) issuer;
            parsedIdealIssuers.add(new IdealIssuer(jsonIssuerObject));
        }

        return parsedIdealIssuers;
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
        return idealIssuers.equals(that.idealIssuers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idealIssuers);
    }
}
