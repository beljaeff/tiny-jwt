package us.cleansite.very.tinyjwt;

import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;
import us.cleansite.very.tinyjwt.algorithm.Algorithm;
import us.cleansite.very.tinyjwt.base64.jdk.Base64EncoderImpl;
import us.cleansite.very.tinyjwt.entity.Payload;
import us.cleansite.very.tinyjwt.json.Serializer;
import us.cleansite.very.tinyjwt.json.jackson.SerializerImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EncoderTest {
    private Encoder encoder;
    private String key = "key";

    @Before
    public void beforeMethod() {
        encoder = Jwt.encoder();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSerializerException() {
        encoder.serializer(null);
    }

    @Test
    public void testSerializer() {
        Serializer given = new SerializerImpl();
        encoder.serializer(given);
        assertNotNull(Whitebox.getInternalState(encoder, "serializer"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEncoderException() {
        encoder.encoder(null);
    }

    @Test
    public void testEncoder() {
        encoder.encoder(new Base64EncoderImpl());
        assertNotNull(Whitebox.getInternalState(encoder, "encoder"));
    }

    @Test
    public void testKey() {
        encoder.key(key);
        String result = (String) Whitebox.getInternalState(encoder, "key");
        assertEquals(key, result);
    }

    @Test
    public void testPayload() {
        PayloadExample payload = new PayloadExample();
        payload.setData("data");
        encoder.payload(payload);
        PayloadExample result = (PayloadExample) Whitebox.getInternalState(encoder, "payload");
        assertEquals(payload, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAlgorithmException() {
        encoder.algorithm(null);
    }

    @Test
    public void testAlgorithm() {
        Algorithm given = Algorithm.HS512;
        encoder.algorithm(given);
        assertNotNull(Whitebox.getInternalState(encoder, "algorithm"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEncodeKeyNull() {
        encoder.payload(new Payload()).key(null).encode();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEncodeKeyEmpty() {
        encoder.payload(new Payload()).key("").encode();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEncodeTokenNull() {
        encoder.payload(null).key(key).encode();
    }

    @Test
    public void testEncodeSuccess() {
        PayloadExample payload = new PayloadExample();
        payload.setData("data");
        assertNotNull(encoder.key(key).payload(new PayloadExample()).encode());
    }
}
