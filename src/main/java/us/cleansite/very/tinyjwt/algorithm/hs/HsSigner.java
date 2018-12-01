package us.cleansite.very.tinyjwt.algorithm.hs;

import us.cleansite.very.tinyjwt.algorithm.Algorithm;
import us.cleansite.very.tinyjwt.algorithm.Signer;
import us.cleansite.very.tinyjwt.exception.SignException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * HmacSHA signer implementation
 */
public class HsSigner extends Signer {

    public HsSigner(Algorithm algorithm) {
        super(algorithm);
    }

    @Override
    public byte[] sign(String secret, String data) {
        try {
            final Mac mac = Mac.getInstance(getAlgorithm());
            final byte[] secretBytes = getBytes(secret);
            final byte[] dataBytes = getBytes(data);

            mac.init(new SecretKeySpec(secretBytes, getAlgorithm()));
            return mac.doFinal(dataBytes);
        }
        catch (NoSuchAlgorithmException | InvalidKeyException | IllegalStateException e) {
            throw new SignException(e);
        }
    }

    private byte[] getBytes(String s) {
        return s.getBytes(UTF_8);
    }
}