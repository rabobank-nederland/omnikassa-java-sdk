package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response;

import org.json.JSONObject;

import java.util.Objects;

/**
 * Contains details about an issuer logo.
 * This can be used for displaying the logo of a particular issuer.
 */
public class IssuerLogo {
    /**
     * A publicly accessible URL where you can download the logo of the issuer.
     */
    private final String url;

    /**
     * The mime type of the logo. This always an image type.
     */
    private final String mimeType;

    public IssuerLogo(JSONObject jsonObject) {
        url = jsonObject.getString("url");
        mimeType = jsonObject.getString("mimeType");
    }

    public String getUrl() {
        return url;
    }

    public String getMimeType() {
        return mimeType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IssuerLogo that = (IssuerLogo) o;
        return url.equals(that.url) && mimeType.equals(that.mimeType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, mimeType);
    }
}
