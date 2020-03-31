package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model;

import org.apache.commons.codec.binary.Base64;
import org.junit.Before;
import org.junit.Test;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions.RabobankSdkException;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class SignableTest {
    private byte[] signingKey;

    @Before
    public void setUp() throws Exception {
        String base64EncodedSigningKey = "jOo9qaeEnQVM";
        signingKey = getSigningKey(base64EncodedSigningKey);
    }

    @Test
    public void testHappyFlow() throws RabobankSdkException {
        String signature = Signable.calculateSignature(asList("foo", "bar"), signingKey);
        String signature1 = Signable.calculateSignature(asList("foo", "bar"), signingKey);
        assertEquals(signature, signature1);
    }

    @Test
    public void caseCapital() throws RabobankSdkException {
        String signature = Signable.calculateSignature(asList("foo", "bar"), signingKey);
        String signature1 = Signable.calculateSignature(asList("Foo", "bar"), signingKey);
        assertNotEquals(signature, signature1);
    }

    @Test
    public void caseDifferentOrder() throws RabobankSdkException {
        String signature = Signable.calculateSignature(asList("foo", "bar"), signingKey);
        String signature1 = Signable.calculateSignature(asList("bar", "foo"), signingKey);
        assertNotEquals(signature, signature1);
    }

    @Test(expected = RabobankSdkException.class)
    public void nullKey() throws RabobankSdkException {
        Signable.calculateSignature(asList("foo", "bar"), null);
    }

    private byte[] getSigningKey(String base64EncodedSigningKey) {
        return Base64.decodeBase64(base64EncodedSigningKey);
    }

}
