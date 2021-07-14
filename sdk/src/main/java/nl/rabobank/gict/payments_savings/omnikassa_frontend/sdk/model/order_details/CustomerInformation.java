package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details;

import org.json.JSONObject;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.JsonConvertible;
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

    CustomerInformation(Builder builder) {
        emailAddress = builder.emailAddress;
        dateOfBirth = builder.dateOfBirth;
        gender = builder.gender;
        initials = builder.initials;
        telephoneNumber = builder.telephoneNumber;
        fullName = builder.fullName;
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

    @Override
    public JSONObject asJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("emailAddress", emailAddress);
        jsonObject.put("dateOfBirth", dateOfBirth);
        jsonObject.put("gender", getNullSafe(gender));
        jsonObject.put("initials", initials);
        jsonObject.put("telephoneNumber", telephoneNumber);
        jsonObject.put("fullName", fullName);

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
        return Objects.hash(emailAddress, dateOfBirth, initials, telephoneNumber, gender, fullName);
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
               Objects.equals(fullName, that.fullName);
    }

    public static class Builder {
        private String emailAddress;
        private Gender gender;
        private String initials;
        private String telephoneNumber;
        private String dateOfBirth;
        private String fullName;

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

        public CustomerInformation build() {
            return new CustomerInformation(this);
        }
    }
}
