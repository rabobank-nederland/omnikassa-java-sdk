package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PaymentBrandInfoTest {

    private PaymentBrandInfo classUnderTest;
    private static final String IDEAL = "IDEAL";
    private static final String ACTIVE = "Active";
    private static final String INACTIVE = "Inactive";

    @Before
    public void setUp() {
        classUnderTest = new PaymentBrandInfo(IDEAL, true);
    }

    @Test
    public void isActiveTrueOK() {
        assertTrue(classUnderTest.isActive());
    }

    @Test
    public void isActiveFalseOK() {
        classUnderTest = new PaymentBrandInfo(IDEAL, false);
        assertFalse(classUnderTest.isActive());
    }

}