package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.connector;

import org.junit.Before;
import org.junit.Test;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions.*;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class ApiConnectorExceptionTest {

    private static final byte[] SIGNING_KEY = "secret".getBytes(UTF_8);

    private ApiConnector classUnderTest;

    @Before
    public void setUp() {
        UnirestJSONTemplate jsonTemplate = new UnirestJSONTemplate("https://betalen.rabobank.nl");
        classUnderTest = new ApiConnector(jsonTemplate, SIGNING_KEY, null, null);
    }

    @Test
    public void testGetOrderStatus() throws Exception {
        String orderId = "3dfe639-967a-473d-8642-fa9347223d7a";

        UnsupportedSandboxOperationException thrown = assertThrows(UnsupportedSandboxOperationException.class, () ->
                classUnderTest.getOrderStatus(orderId, "token"));

        assertTrue(thrown.getMessage().contains("This request is not supported for SANDBOX environment"));
    }
}
