package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Contains details about an issuer.
 */
public class Issuer {
    /**
     * The id of the issuer, which is meant for machines.
     * Use this if you want to communicate this particular issuer with an API endpoint.
     */
    private final String id;

    /**
     * The name of the issuer in human readable form.
     */
    private final String name;

    /**
     * Can potentially contain multiple formats of the same logo.
     */
    private final List<IssuerLogo> logos;

    private final String countryNames;

    public Issuer(JSONObject jsonObject) {
        id = jsonObject.getString("id");
        name = jsonObject.getString("name");
        logos = parseLogos(jsonObject.getJSONArray("logos"));
        countryNames = jsonObject.getString("countryNames");
    }

    private List<IssuerLogo> parseLogos(JSONArray jsonLogosArray) {
        if (jsonLogosArray == null) {
            return Collections.emptyList();
        }

        List<IssuerLogo> parsedLogos = new ArrayList<>();
        for (Object logo: jsonLogosArray) {
            JSONObject jsonLogo = (JSONObject) logo;
            parsedLogos.add(new IssuerLogo(jsonLogo));
        }

        return parsedLogos;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<IssuerLogo> getLogos() {
        return logos;
    }

    public String getCountryNames() {
        return countryNames;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Issuer issuer = (Issuer) o;
        return id.equals(issuer.id) && name.equals(issuer.name) && logos.equals(issuer.logos) && countryNames.equals(issuer.countryNames);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, logos, countryNames);
    }
}
