package nl.rabobank.gict.payments_savings.omnikassa_frontend.test.webshop.model;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.connector.TokenProvider;

import java.util.HashMap;
import java.util.Map;

public class CustomTokenProvider extends TokenProvider {

    private Map<FieldName, String> map = new HashMap<>();

    public CustomTokenProvider(String refreshToken) {
        setValue(FieldName.REFRESH_TOKEN, refreshToken);
    }

    @Override
    public String getValue(FieldName key) {
        return map.get(key);
    }

    @Override
    public void setValue(FieldName key, String value) {
        map.put(key, value);
    }
}
