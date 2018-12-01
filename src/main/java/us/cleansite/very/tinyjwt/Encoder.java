package us.cleansite.very.tinyjwt;

import us.cleansite.very.tinyjwt.algorithm.Algorithm;
import us.cleansite.very.tinyjwt.base64.Base64Encoder;
import us.cleansite.very.tinyjwt.base64.jdk.Base64EncoderImpl;
import us.cleansite.very.tinyjwt.entity.Payload;
import us.cleansite.very.tinyjwt.json.Serializer;
import us.cleansite.very.tinyjwt.json.jackson.SerializerImpl;

/**
 * Builds encoded string using given data
 */
public class Encoder {

    private static final String DEFAULT_TYPE = "JWT";

    private Serializer serializer = new SerializerImpl();
    private Base64Encoder encoder = new Base64EncoderImpl();

    private String key;
    private Algorithm algorithm = Algorithm.HS512;
    private Payload payload;

    Encoder() {}

    public Encoder serializer(Serializer serializer) {
        if(serializer == null) {
            throw new IllegalArgumentException("Serializer should be set");
        }
        this.serializer = serializer;
        return this;
    }

    public Encoder encoder(Base64Encoder encoder) {
        if(encoder == null) {
            throw new IllegalArgumentException("Encoder should be set");
        }
        this.encoder = encoder;
        return this;
    }

    public Encoder payload(Payload payload) {
        this.payload = payload;
        return this;
    }

    public Encoder key(String key) {
        this.key = key;
        return this;
    }

    public Encoder algorithm(Algorithm algorithm) {
        if(algorithm == null) {
            throw new IllegalArgumentException("Algorithm should be set");
        }
        this.algorithm = algorithm;
        return this;
    }

    public String encode() {
        if(key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Key should be set");
        }
        if(payload == null) {
            throw new IllegalArgumentException("Payload should be set");
        }
        return TokenBuilder.encodeBuilder()
                .header(algorithm, DEFAULT_TYPE)
                .payload(payload)
                .encode(serializer, encoder)
                .build(key, encoder);
    }
}