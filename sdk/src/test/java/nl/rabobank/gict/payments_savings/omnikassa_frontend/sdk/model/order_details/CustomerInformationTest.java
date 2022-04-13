package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details;

import nl.jqno.equalsverifier.EqualsVerifier;
import kong.unirest.json.JSONObject;
import org.junit.Test;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.JsonFileConverter;

import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.Gender.M;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details.CustomerInformationFactory.customerInformation;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details.CustomerInformationFactory.customerInformationFull;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;

public class CustomerInformationTest {
    private final CustomerInformation customerInformation = customerInformation().build();
    private final CustomerInformation customerInformationFull = customerInformationFull();
    private final JsonFileConverter converter = new JsonFileConverter();

    @Test
    public void testFields() {
        assertThat(customerInformation.getDateOfBirth(), nullValue());
        assertThat(customerInformation.getEmailAddress(), nullValue());
        assertThat(customerInformation.getGender(), nullValue());
        assertThat(customerInformation.getInitials(), nullValue());
        assertThat(customerInformation.getTelephoneNumber(), nullValue());
        assertThat(customerInformation.getFullName(), nullValue());
    }

    @Test
    public void testFieldsFull() {
        assertThat(customerInformationFull.getDateOfBirth(), is("18-12-2003"));
        assertThat(customerInformationFull.getEmailAddress(), is("jan.de.ruiter@example.com"));
        assertThat(customerInformationFull.getGender(), is(M));
        assertThat(customerInformationFull.getInitials(), is("J."));
        assertThat(customerInformationFull.getTelephoneNumber(), is("0031204111111"));
        assertThat(customerInformationFull.getFullName(), is("Jan de Ruiter"));
    }

    @Test
    public void asJson_Should_ReturnCorrectJsonObject() {
        JSONObject actualJson = customerInformation.asJson();
        JSONObject expectedJson = new JSONObject();
        expectedJson.put("emailAddress",(String)null);
        expectedJson.put("dateOfBirth",(String)null);
        expectedJson.put("gender",(String)null);
        expectedJson.put("initials",(String)null);
        expectedJson.put("telephoneNumber",(String)null);
        expectedJson.put("fullName",(String)null);

        assertEquals(actualJson, expectedJson);
    }

    @Test
    public void asJson_Should_ReturnCorrectJsonObject_full() {
        JSONObject actualJson = customerInformationFull.asJson();
        JSONObject expectedJson = converter.convert("customer_information_full.json");

        assertEquals(actualJson, expectedJson);
    }

    @Test
    public void equalsHashCodeContract() {
        EqualsVerifier.forClass(CustomerInformation.class).usingGetClass().verify();
    }
}