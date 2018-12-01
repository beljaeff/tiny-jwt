package us.cleansite.very.tinyjwt;

import us.cleansite.very.tinyjwt.base64.Base64Decoder;
import us.cleansite.very.tinyjwt.base64.Base64Encoder;
import us.cleansite.very.tinyjwt.base64.jdk.Base64DecoderImpl;
import us.cleansite.very.tinyjwt.base64.jdk.Base64EncoderImpl;
import us.cleansite.very.tinyjwt.entity.Payload;
import us.cleansite.very.tinyjwt.exception.TokenExpiredException;
import us.cleansite.very.tinyjwt.exception.TokenFormatException;
import us.cleansite.very.tinyjwt.json.Deserializer;
import us.cleansite.very.tinyjwt.json.jackson.DeserializerImpl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Arrays;

/**
 * Builds decoded JWT payload from encoded string
 */
public class Decoder {

    private Deserializer deserializer = new DeserializerImpl();
    private Base64Encoder encoder = new Base64EncoderImpl();
    private Base64Decoder decoder = new Base64DecoderImpl();

    private String key;
    private String token;

    Decoder() {}

    public Decoder deserializer(Deserializer deserializer) {
        if (deserializer == null) {
            throw new IllegalArgumentException("Deserializer should be set");
        }
        this.deserializer = deserializer;
        return this;
    }

    public Decoder encoder(Base64Encoder encoder) {
        if (encoder == null) {
            throw new IllegalArgumentException("Encoder should be set");
        }
        this.encoder = encoder;
        return this;
    }

    public Decoder decoder(Base64Decoder decoder) {
        if (decoder == null) {
            throw new IllegalArgumentException("Decoder should be set");
        }
        this.decoder = decoder;
        return this;
    }

    public Decoder key(String key) {
        this.key = key;
        return this;
    }

    public Decoder token(String token) {
        this.token = token;
        return this;
    }

    public <T extends Payload> T decode(Class<T> cl) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Key should be set");
        }
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("token should be set");
        }

        String[] segments = token.split("\\.");
        if(segments.length < 3 || Arrays.stream(segments).anyMatch(String::isEmpty)) {
            throw new TokenFormatException();
        }

        T payload = cl.cast(TokenBuilder
                .<T>decodeBuilder(segments)
                .decode(decoder, deserializer, cl)
                .verify(key, encoder)
                .build());

        // Check payload "not before" (start) claim
        checkTime(payload.getNbf(), 1);

        // Check payload "expires" claim
        checkTime(payload.getExp(), -1);

        return payload;
    }

    /**
     * Checks timestamp against current time
     * @param strTime - time to check
     * @param sign:
     *            -1 => currentTime < strTime == true
     *             1 => currentTime > strTime == true
     */
    private void checkTime(String strTime, int sign) {
        if(strTime != null) {
            try {
                long time = Long.valueOf(strTime);
                long currentTime = LocalDateTime
                        .now()
                        .toEpochSecond(ZoneOffset.UTC);
                if (currentTime*sign < time*sign) {
                    throw new TokenExpiredException();
                }
            }
            catch (NumberFormatException nfe) {
                throw new TokenFormatException(nfe);
            }
        }
    }
}
