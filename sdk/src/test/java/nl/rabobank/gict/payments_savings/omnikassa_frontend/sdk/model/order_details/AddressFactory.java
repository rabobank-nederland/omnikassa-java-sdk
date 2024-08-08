package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details.Address.Builder;

import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.CountryCode.NL;

public class AddressFactory {
    public static Address addressFull() {
        return defaultBuilder()
                .withMiddleName("de")
                .withStreet("Kalverstraat")
                .withHouseNumber("1")
                .withHouseNumberAddition("a")
                .build();
    }

    public static Address defaultAddress() {
        return defaultBuilder().build();
    }

    private static Builder defaultBuilder() {
        return new Builder()
                .withFirstName("Jan")
                .withLastName("Ruiter")
                .withStreet("Kalverstraat 1")
                .withPostalCode("1012NX")
                .withCity("Amsterdam")
                .withCountryCode(NL);
    }
}
