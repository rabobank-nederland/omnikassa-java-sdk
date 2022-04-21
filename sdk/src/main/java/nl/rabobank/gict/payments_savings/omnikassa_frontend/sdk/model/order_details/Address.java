package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details;
import kong.unirest.json.JSONObject;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.JsonConvertible;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.CountryCode;

import java.util.Objects;

/**
 * This class is used to contain the customer address details. You could use this class for shipping and/or billing details.
 */
public class Address implements JsonConvertible {
    private final String firstName;
    private final String middleName;
    private final String lastName;
    private final String street;
    private final String houseNumber;
    private final String houseNumberAddition;
    private final String postalCode;
    private final String city;
    private final CountryCode countryCode;

    public Address(Builder builder) {
        firstName = builder.firstName;
        middleName = builder.middleName;
        lastName = builder.lastName;
        street = builder.street;
        houseNumber = builder.houseNumber;
        houseNumberAddition = builder.houseNumberAddition;
        postalCode = builder.postalCode;
        city = builder.city;
        countryCode = builder.countryCode;
    }

    @Override
    public JSONObject asJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("firstName", firstName);
        jsonObject.put("middleName", middleName);
        jsonObject.put("lastName", lastName);
        jsonObject.put("street", street);
        jsonObject.put("houseNumber", houseNumber);
        jsonObject.put("houseNumberAddition", houseNumberAddition);
        jsonObject.put("postalCode", postalCode);
        jsonObject.put("city", city);
        jsonObject.put("countryCode", countryCode.toString());
        return jsonObject;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getStreet() {
        return street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getHouseNumberAddition() {
        return houseNumberAddition;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }

    public CountryCode getCountryCode() {
        return countryCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if(o == null){
            return false;
        }
        if (this.getClass() !=  o.getClass()) {
            return false;
        }
        Address address = (Address) o;
        return Objects.equals(firstName, address.firstName) &&
               Objects.equals(middleName, address.middleName) &&
               Objects.equals(lastName, address.lastName) &&
               Objects.equals(street, address.street) &&
               Objects.equals(houseNumber, address.houseNumber) &&
               Objects.equals(houseNumberAddition, address.houseNumberAddition) &&
               Objects.equals(postalCode, address.postalCode) &&
               Objects.equals(city, address.city) &&
               countryCode == address.countryCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, middleName, lastName, street, houseNumber, houseNumberAddition, postalCode, city, countryCode);
    }

    public static class Builder {
        private String firstName;
        private String middleName;
        private String lastName;
        private String street;
        private String houseNumber;
        private String houseNumberAddition;
        private String postalCode;
        private String city;
        private CountryCode countryCode;

        /**
         * @param firstName | Must not be 'null'
         *                  | Must be a valid name
         *                  | Maximum length of '50' characters
         * @return Builder
         */
        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        /**
         * @param middleName | Optional
         *                   | Should contain only text characters
         *                   | Maximum length of '20' characters
         * @return Builder
         */
        public Builder withMiddleName(String middleName) {
            this.middleName = middleName;
            return this;
        }

        /**
         * @param lastName | Should not be `null` or empty
         *                 | Should contain only text characters
         *                 | Maximum length of '50' characters
         * @return Builder
         */
        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        /**
         * @param street | Must not be 'null'
         *               | Must be a valid street
         *               | Maximum length of '100' characters
         * @return Builder
         */
        public Builder withStreet(String street) {
            this.street = street;
            return this;
        }

        /**
         * @param houseNumber | Optional
         *                    | Must be a valid houseNumber
         * @return Builder
         */
        public Builder withHouseNumber(String houseNumber) {
            this.houseNumber = houseNumber;
            return this;
        }

        /**
         * @param houseNumberAddition | Optional
         *                            | Must be a valid houseNumber
         *                            | Maximum length of '6' characters
         * @return Builder
         */
        public Builder withHouseNumberAddition(String houseNumberAddition) {
            this.houseNumberAddition = houseNumberAddition;
            return this;
        }

        /**
         * @param postalCode | Must not be 'null'
         *                   | Must be a valid postalCode for the Country
         *                   | Maximum length is dependant on the countryCode:
         *                   CountryCode.BE = '4' , CountryCode.DE = '5' , CountryCode.NL = '6'
         * @return Builder
         */
        public Builder withPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        /**
         * @param city | Must not be 'null'
         *             | Must only contain alphabetic characters
         *             | Maximum length of '40' characters
         * @return Builder
         */
        public Builder withCity(String city) {
            this.city = city;
            return this;
        }

        /**
         * @param countryCode | Must not be 'null'
         *                    | Must be a valid CountryCode
         * @return Builder
         */
        public Builder withCountryCode(CountryCode countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        public Address build() {
            return new Address(this);
        }
    }
}
