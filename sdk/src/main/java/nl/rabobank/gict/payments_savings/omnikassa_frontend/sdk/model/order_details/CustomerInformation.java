package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details;

import kong.unirest.json.JSONObject;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.JsonConvertible;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.CountryCode;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.Gender;

import java.util.Objects;

/**
 * This class is used to contain the customer information.
 */
public final class CustomerInformation implements JsonConvertible {
    private final String emailAddress;
    private final String dateOfBirth;
    private final String initials;
    private final String telephoneNumber;
    private final Gender gender;
    private final String fullName;
    private final String companyName;
    private final String companyNumber;
    private final String companyVatNumber;
    private final CountryCode companyCountryCode;

    CustomerInformation(Builder builder) {
        emailAddress = builder.emailAddress;
        dateOfBirth = builder.dateOfBirth;
        gender = builder.gender;
        initials = builder.initials;
        telephoneNumber = builder.telephoneNumber;
        fullName = builder.fullName;
        companyName = builder.companyName;
        companyNumber = builder.companyNumber;
        companyVatNumber = builder.companyVatNumber;
        companyCountryCode = builder.companyCountryCode;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public Gender getGender() {
        return gender;
    }

    public String getInitials() {
        return initials;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getFullName() {
        return fullName;
    }

    public String getCompanyName() {
        return companyName;
    }
    public String getCompanyNumber() {
        return companyNumber;
    }
    public String getCompanyVatNumber() {
        return companyVatNumber;
    }
    public CountryCode getCompanyCountryCode() {
        return companyCountryCode;
    }

    @Override
    public JSONObject asJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("emailAddress", emailAddress);
        jsonObject.put("dateOfBirth", dateOfBirth);
        jsonObject.put("gender", getNullSafe(gender));
        jsonObject.put("initials", initials);
        jsonObject.put("telephoneNumber", telephoneNumber);
        jsonObject.put("fullName", fullName);
        jsonObject.put("companyName", companyName);
        jsonObject.put("companyNumber", companyNumber);
        jsonObject.put("companyVatNumber", companyVatNumber);
        jsonObject.put("companyCountryCode", companyCountryCode);

        return jsonObject;
    }

    private String getNullSafe(Enum value) {
        if (value == null) {
            return null;
        }
        return value.name();
    }

    @Override
    public int hashCode() {
        return Objects.hash(emailAddress, dateOfBirth, initials, telephoneNumber, gender, fullName, companyName, companyNumber, companyVatNumber, companyCountryCode);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerInformation)) {
            return false;
        }
        CustomerInformation that = (CustomerInformation) o;
        return Objects.equals(emailAddress, that.emailAddress) &&
               Objects.equals(dateOfBirth, that.dateOfBirth) &&
               Objects.equals(initials, that.initials) &&
               Objects.equals(telephoneNumber, that.telephoneNumber) &&
               gender == that.gender &&
               Objects.equals(fullName, that.fullName) &&
               Objects.equals(companyName, that.companyName) &&
               Objects.equals(companyNumber, that.companyNumber) &&
               Objects.equals(companyVatNumber, that.companyVatNumber) &&
               Objects.equals(companyCountryCode, that.companyCountryCode);
    }

    public static class Builder {
        private String emailAddress;
        private Gender gender;
        private String initials;
        private String telephoneNumber;
        private String dateOfBirth;
        private String fullName;
        private String companyName;
        private String companyNumber;
        private String companyVatNumber;
        private CountryCode companyCountryCode;

        /**
         * @param emailAddress | Optional
         *                     | Must be a valid email address
         *                     | Maximum length of '45' characters
         * @return Builder
         */
        public Builder withEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
            return this;
        }

        /**
         * @param gender | Optional
         *               | Must be one of the {@link Gender} values
         * @return Builder
         */
        public Builder withGender(Gender gender) {
            this.gender = gender;
            return this;
        }

        /**
         * @param initials | Optional
         *                 | Must only contain alphabetic characters
         *                 | Maximum length of '256' characters
         * @return Builder
         */
        public Builder withInitials(String initials) {
            this.initials = initials;
            return this;
        }

        /**
         * @param telephoneNumber | Optional
         *                        | Must be a valid telephone number
         *                        | Must only contain alphabetic or numeric characters
         *                        | Maximum length of '31' characters
         * @return Builder
         */
        public Builder withTelephoneNumber(String telephoneNumber) {
            this.telephoneNumber = telephoneNumber;
            return this;
        }

        /**
         * @param dateOfBirth | Optional
         *                    | Must be a valid birth date
         *                    | Only the DD-MM-YYYY format is accepted
         * @return Builder
         */
        public Builder withDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        /**
         * @param fullName | Optional
         *                 | Maximum length of '128' characters
         * @return Builder
         */
        public Builder withFullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        /**
         * @param companyName | Optional
         *                 | Maximum length of '50' characters
         * @return Builder
         */
        public Builder withCompanyName(String companyName) {
            this.companyName = companyName;
            return this;
        }
        /**
         * @param companyNumber | Optional
         *                 | Maximum length of '255' characters
         * @return Builder
         */
        public Builder withCompanyNumber(String companyNumber) {
            this.companyNumber = companyNumber;
            return this;
        }
        /**
         * @param companyVatNumber | Optional
         *                 | Maximum length of '50' characters
         * @return Builder
         */
        public Builder withCompanyVatNumber(String companyVatNumber) {
            this.companyVatNumber = companyVatNumber;
            return this;
        }
        /**
         * @param countryCode | Optional
         *                    | Must be a valid CountryCode
         * @return Builder
         */
        public Builder withCompanyCountryCode(CountryCode countryCode) {
            this.companyCountryCode = countryCode;
            return this;
        }

        public CustomerInformation build() {
            return new CustomerInformation(this);
        }
    }
}
