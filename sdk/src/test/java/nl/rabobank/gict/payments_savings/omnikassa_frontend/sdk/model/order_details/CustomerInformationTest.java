package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.json.JSONObject;
import org.junit.Test;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.JsonFileConverter;

import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.Gender.M;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details.CustomerInformationFactory.customerInformation;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details.CustomerInformationFactory.customerInformationFull;
import static org.apache.commons.lang3.StringUtils.join;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

public class CustomerInformationTest {
    private CustomerInformation customerInformation = customerInformation().build();
    private CustomerInformation customerInformationFull = customerInformationFull();
    private JsonFileConverter converter = new JsonFileConverter();

    @Test
    public void testFields() {
        assertThat(customerInformation.getDateOfBirth(), nullValue());
        assertThat(customerInformation.getEmailAddress(), nullValue());
        assertThat(customerInformation.getGender(), nullValue());
        assertThat(customerInformation.getInitials(), nullValue());
        assertThat(customerInformation.getTelephoneNumber(), nullValue());
    }

    @Test
    public void testFieldsFull() {
        assertThat(customerInformationFull.getDateOfBirth(), is("18-12-2003"));
        assertThat(customerInformationFull.getEmailAddress(), is("jan.de.ruiter@example.com"));
        assertThat(customerInformationFull.getGender(), is(M));
        assertThat(customerInformationFull.getInitials(), is("J."));
        assertThat(customerInformationFull.getTelephoneNumber(), is("0031204111111"));
    }

    @Test
    public void asJson_Should_ReturnCorrectJsonObject() {
        JSONObject actualJson = customerInformation.asJson();
        JSONObject expectedJson = new JSONObject();

        assertEquals(actualJson, expectedJson, true);
    }

    @Test
    public void asJson_Should_ReturnCorrectJsonObject_full() {
        JSONObject actualJson = customerInformationFull.asJson();
        JSONObject expectedJson = converter.convert("customer_information_full.json");

        assertEquals(actualJson, expectedJson, true);
    }

    @Test
    public void equalsHashCodeContract() {
        EqualsVerifier.forClass(CustomerInformation.class).usingGetClass().verify();
    }
}