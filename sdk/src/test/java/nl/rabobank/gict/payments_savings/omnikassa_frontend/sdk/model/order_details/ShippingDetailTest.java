package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details;

import org.junit.Test;

import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.CountryCode.NL;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class ShippingDetailTest {

    @Test
    public void create() {
        ShippingDetail detail = new ShippingDetail("Jan", "de", "Ruiter", "Kalverstraat 1", "1012NX", "Amsterdam", NL);

        assertThat(detail.getFirstName(), is("Jan"));
        assertThat(detail.getMiddleName(), is("de"));
        assertThat(detail.getLastName(), is("Ruiter"));
        assertThat(detail.getStreet(), is("Kalverstraat 1"));
        assertThat(detail.getPostalCode(), is("1012NX"));
        assertThat(detail.getCity(), is("Amsterdam"));
        assertThat(detail.getCountryCode(), is(NL));
        assertThat(detail.getHouseNumber(), nullValue());
        assertThat(detail.getHouseNumberAddition(), nullValue());
    }
}