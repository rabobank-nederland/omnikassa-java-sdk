package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details.CustomerInformation.Builder;

import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.Gender.M;

public class CustomerInformationFactory {
    public static CustomerInformation customerInformationFull() {
        return customerInformation()
                .withDateOfBirth("18-12-2003")
                .withGender(M)
                .withEmailAddress("jan.de.ruiter@example.com")
                .withInitials("J.")
                .withTelephoneNumber("0031204111111")
                .withFullName("Jan de Ruiter")
                .build();
    }

    public static Builder customerInformation() {
        return defaultBuilder();
    }

    private static Builder defaultBuilder() {
        return new Builder();
    }
}
