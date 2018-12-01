package us.cleansite.very.tinyjwt.algorithm.hs;

import org.junit.Before;
import org.junit.Test;
import us.cleansite.very.tinyjwt.algorithm.Algorithm;
import us.cleansite.very.tinyjwt.exception.SignException;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class HsSignerTest {

    private final String secret = "secret";
    private final String data = "data";
    private final Algorithm algorithm = Algorithm.HS512;

    private HsSigner spySigner;

    @Before
    public void beforeMethod() {
        HsSigner signer = new HsSigner(algorithm);
        spySigner = spy(signer);
    }

    /**
     * SuppressWarning("unchecked") required to suppress
     * "Unchecked generics array creation for varargs parameter" during mocking
     */
    @SuppressWarnings("unchecked")
    private void makeExceptionCall(Class<?> c) {
        when(spySigner.getAlgorithm()).thenThrow(NoSuchAlgorithmException.class);
        spySigner.sign(secret, data);
    }

    @Test(expected = SignException.class)
    public void testSignNoSuchAlgorithmException() {
        makeExceptionCall(NoSuchAlgorithmException.class);
    }

    @Test(expected = SignException.class)
    public void testSignInvalidKeyException() {
        makeExceptionCall(InvalidKeyException.class);
    }

    @Test(expected = SignException.class)
    public void testSignIllegalStateException() {
        makeExceptionCall(IllegalStateException.class);
    }

    @Test
    public void testSign() {
        assertNotNull(spySigner.sign(secret, data));
    }
}