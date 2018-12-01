package us.cleansite.very.tinyjwt;

import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;
import us.cleansite.very.tinyjwt.base64.jdk.Base64DecoderImpl;
import us.cleansite.very.tinyjwt.base64.jdk.Base64EncoderImpl;
import us.cleansite.very.tinyjwt.entity.Payload;
import us.cleansite.very.tinyjwt.exception.TokenExpiredException;
import us.cleansite.very.tinyjwt.exception.TokenFormatException;
import us.cleansite.very.tinyjwt.json.Deserializer;
import us.cleansite.very.tinyjwt.json.jackson.DeserializerImpl;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DecoderTest {

    private Decoder decoder;
    private String key = "key";

    private String getToken(PayloadExample payload) {
        return Jwt.encoder()
                .payload(payload)
                .key(key)
                .encode();
    }

    private PayloadExample decodeToken(String token) {
        return decoder.key(key).token(token).decode(PayloadExample.class);
    }

    private String getStrTime(int year) {
        return String.valueOf(
                LocalDateTime
                        .of(year, 1, 1, 1, 1, 1)
                        .toEpochSecond(ZoneOffset.UTC));
    }

    @Before
    public void beforeMethod() {
        decoder = Jwt.decoder();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeserializerException() {
        decoder.deserializer(null);
    }

    @Test
    public void testDeserializer() {
        Deserializer given = new DeserializerImpl();
        decoder.deserializer(given);
        assertNotNull(Whitebox.getInternalState(decoder, "deserializer"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEncoderException() {
        decoder.encoder(null);
    }

    @Test
    public void testEncoder() {
        decoder.encoder(new Base64EncoderImpl());
        assertNotNull(Whitebox.getInternalState(decoder, "encoder"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDecoderException() {
        decoder.decoder(null);
    }

    @Test
    public void testDecoder() {
        decoder.decoder(new Base64DecoderImpl());
        assertNotNull(Whitebox.getInternalState(decoder, "decoder"));
    }

    @Test
    public void testKey() {
        decoder.key(key);
        String result = (String) Whitebox.getInternalState(decoder, "key");
        assertEquals(key, result);
    }

    @Test
    public void testToken() {
        String token = "token";
        decoder.token(token);
        String result = (String) Whitebox.getInternalState(decoder, "token");
        assertEquals(token, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDecodeKeyNull() {
        decoder.token("token").key(null).decode(Payload.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDecodeKeyEmpty() {
        decoder.token("token").key("").decode(Payload.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDecodeTokenNull() {
        decoder.key(key).token(null).decode(Payload.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDecodeTokenEmpty() {
        decoder.key(key).token("").decode(Payload.class);
    }

    @Test(expected = TokenFormatException.class)
    public void testDecodeSegmentsLength() {
        String token = "a";
        decodeToken(token);
    }

    @Test(expected = TokenFormatException.class)
    public void testDecodeSegmentEmpty() {
        String token = "a..a";
        decodeToken(token);
    }

    @Test(expected = TokenExpiredException.class)
    public void testDecodeIncorrectNotBefore() {
        PayloadExample payload = new PayloadExample();
        payload.setNbf(getStrTime(2110));

        String token = getToken(payload);
        decodeToken(token);
    }

    @Test(expected = TokenFormatException.class)
    public void testDecodeIncorrectNotBeforeDateFormat() {
        PayloadExample payload = new PayloadExample();
        payload.setNbf("broken date");

        String token = getToken(payload);
        decodeToken(token);
    }

    @Test(expected = TokenExpiredException.class)
    public void testDecodeIncorrectExpired() {
        PayloadExample payload = new PayloadExample();
        payload.setExp(getStrTime(2010));

        String token = getToken(payload);
        decodeToken(token);
    }

    @Test(expected = TokenFormatException.class)
    public void testDecodeIncorrectExpiredDateFormat() {
        PayloadExample payload = new PayloadExample();
        payload.setExp("broken date");

        String token = getToken(payload);
        decodeToken(token);
    }

    @Test
    public void testDecodeSuccess() {
        PayloadExample payload = new PayloadExample();
        payload.setData("data");
        payload.setExp(getStrTime(2100));
        payload.setNbf(getStrTime(2000));

        String token = getToken(payload);
        PayloadExample result = decodeToken(token);
        assertEquals(payload, result);
    }
}