package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details;


import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.CountryCode;

/**
 * @deprecated Use the {@link Address} instead of this class.
 */
@Deprecated
public final class ShippingDetail extends Address {

    public ShippingDetail(String firstName,
                          String middleName,
                          String lastName,
                          String street,
                          String postalCode,
                          String city,
                          CountryCode countryCode) {
        super(createAddressFactory(firstName, middleName, lastName, street, postalCode, city, countryCode));
    }

    private static Builder createAddressFactory(String firstName,
                                                String middleName,
                                                String lastName,
                                                String street,
                                                String postalCode,
                                                String city,
                                                CountryCode countryCode) {
        return new Builder()
                      .withFirstName(firstName)
                      .withMiddleName(middleName)
                      .withLastName(lastName)
                      .withStreet(street)
                      .withPostalCode(postalCode)
                      .withCity(city)
                      .withCountryCode(countryCode);
    }
}