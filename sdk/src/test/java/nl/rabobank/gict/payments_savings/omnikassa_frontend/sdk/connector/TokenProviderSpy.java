package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.connector;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.AccessToken;

import java.util.Calendar;
import java.util.Map;

public class TokenProviderSpy extends TokenProvider {
    private Map<FieldName, String> map;

    public TokenProviderSpy(Map<FieldName, String> map) {
        this.map = map;
    }

    @Override
    public String getValue(FieldName key) {
        return map.get(key);
    }

    @Override
    public void setValue(FieldName key, String value) {
        map.put(key, value);
    }

    public void setValidAccessToken() {
        setAccessToken(new AccessToken("test", getTomorrow(), 12800));
    }

    private Calendar getTomorrow() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 1);
        return c;
    }
}
