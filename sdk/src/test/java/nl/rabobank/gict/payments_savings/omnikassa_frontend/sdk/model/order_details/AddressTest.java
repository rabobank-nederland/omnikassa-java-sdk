package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details;

import kong.unirest.json.JSONObject;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.JsonFileConverter;

import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.CountryCode.NL;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details.AddressFactory.addressFull;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details.AddressFactory.defaultAddress;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertEquals;

public class AddressTest {
    private Address address = defaultAddress();
    private Address addressFull = addressFull();
    private JsonFileConverter converter = new JsonFileConverter();

    @Test
    public void testFields() {
        assertThat(address.getFirstName(), is("Jan"));
        assertThat(address.getMiddleName(), nullValue());
        assertThat(address.getLastName(), is("Ruiter"));
        assertThat(address.getStreet(), is("Kalverstraat 1"));
        assertThat(address.getHouseNumber(), nullValue());
        assertThat(address.getHouseNumberAddition(), nullValue());
        assertThat(address.getPostalCode(), is("1012NX"));
        assertThat(address.getCity(), is("Amsterdam"));
        assertThat(address.getCountryCode(), is(NL));
    }

    @Test
    public void testFieldsFull() {
        assertThat(addressFull.getFirstName(), is("Jan"));
        assertThat(addressFull.getMiddleName(), is("de"));
        assertThat(addressFull.getLastName(), is("Ruiter"));
        assertThat(addressFull.getStreet(), is("Kalverstraat"));
        assertThat(addressFull.getHouseNumber(), is("1"));
        assertThat(addressFull.getHouseNumberAddition(), is("a"));
        assertThat(addressFull.getPostalCode(), is("1012NX"));
        assertThat(addressFull.getCity(), is("Amsterdam"));
        assertThat(addressFull.getCountryCode(), is(NL));
    }

    @Test
    public void asJson_Should_ReturnCorrectJsonObject() {
        JSONObject expectedJson =  converter.convert("address.json");
        JSONObject actualJson = address.asJson();
        assertEquals(expectedJson, actualJson);
    }

    @Test
    public void asJson_Should_ReturnCorrectJsonObject_full() {
        JSONObject expectedJson = converter.convert("address_full.json");
        JSONObject actualJson = addressFull.asJson();
        assertEquals(expectedJson, actualJson);
    }

    @Test
    public void equalsHashCodeContract() {
        EqualsVerifier.forClass(Address.class).usingGetClass().verify();
    }
}
