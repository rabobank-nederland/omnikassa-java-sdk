package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response;

import kong.unirest.json.JSONObject;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions.IllegalSignatureException;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions.RabobankSdkException;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.Signable;

import java.security.MessageDigest;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * this class is used to validate the signature of a response
 */
public abstract class SignedResponse extends Signable {

    SignedResponse(JSONObject jsonObject) {
        setSignature(jsonObject.getString("signature"));
    }

    SignedResponse(String signature) {
        setSignature(signature);
    }

    /**
     * this function validates if the signature of the object is the same as the signature from the api response.
     * @param signingKey The signing key to validate the signature with.
     * @throws RabobankSdkException when the signature is invalid.
     */
    public void validateSignature(byte[] signingKey) throws RabobankSdkException {
        String calculatedSignature = calculateSignature(getSignatureData(), signingKey);

        if (!MessageDigest.isEqual(calculatedSignature.getBytes(UTF_8), getSignature().getBytes(UTF_8))) {
            throw new IllegalSignatureException();
        }
    }
}