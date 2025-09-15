package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk;

import org.junit.Test;

import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.Environment.PRODUCTION;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.Environment.SANDBOX;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class EnvironmentTest {

    @Test
    public void getUrl() {
        assertThat(PRODUCTION.getUrl(), is("https://api.pay.rabobank.nl"));
        assertThat(SANDBOX.getUrl(), is("https://api.pay-sandbox.rabobank.nl"));
    }
}