package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions.RabobankSdkException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * This class handles the calculation of the signature of the classes that are extended by it.
 */
public abstract class Signable {
    private static final String HASH_ALGORITHM = "HmacSHA512";
    private String signature;

    protected abstract List<String> getSignatureData();

    public final void setSignature(String signature) {
        this.signature = signature;
    }

    protected String getSignature() {
        return signature;
    }

    public static String calculateSignature(List<String> parts, byte[] signingKey) throws RabobankSdkException {
        String payload = StringUtils.join(parts, ",");
        try {
            Mac instance = Mac.getInstance(HASH_ALGORITHM);
            SecretKeySpec secretKey = new SecretKeySpec(signingKey, HASH_ALGORITHM);
            instance.init(secretKey);
            byte[] result = instance.doFinal(payload.getBytes(UTF_8));
            return Hex.encodeHexString(result);
        } catch (Exception anyException) {
            throw new RabobankSdkException(anyException);
        }
    }

}
