package nl.rabobank.gict.payments_savings.omnikassa_frontend.test.webshop.model;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.connector.TokenProvider;

import java.util.EnumMap;

public class CustomTokenProvider extends TokenProvider {

    private final EnumMap<FieldName, String> map = new EnumMap<>(FieldName.class);

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
