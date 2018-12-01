package us.cleansite.very.tinyjwt;

import us.cleansite.very.tinyjwt.algorithm.Algorithm;
import us.cleansite.very.tinyjwt.algorithm.Signer;
import us.cleansite.very.tinyjwt.algorithm.hs.HsSigner;
import us.cleansite.very.tinyjwt.base64.Base64Decoder;
import us.cleansite.very.tinyjwt.base64.Base64Encoder;
import us.cleansite.very.tinyjwt.entity.Header;
import us.cleansite.very.tinyjwt.entity.Payload;
import us.cleansite.very.tinyjwt.exception.SignerCreatingException;
import us.cleansite.very.tinyjwt.exception.VerificationException;
import us.cleansite.very.tinyjwt.json.Deserializer;
import us.cleansite.very.tinyjwt.json.Serializer;

import java.lang.reflect.InvocationTargetException;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Token builder facade
 */
class TokenBuilder {

    static EncodeBuilder encodeBuilder() {
        return new EncodeBuilder();
    }

    static <T extends Payload> DecodeBuilder<T> decodeBuilder(String[] segments) {
        return new DecodeBuilder<>(segments);
    }

    private abstract static class Builder<T extends Payload> {
        Signer signer = new HsSigner(Algorithm.HS512);

        Header header;
        T payload;
        String signature;

        /**
         * Inner structure, stores parts of JWT token, translated into string
         * [0] - header
         * [1] - payload
         * [2] - signature
         */
        String[] segments;

        void sign(String key, Base64Encoder encoder) {
            signature =
                encoder.encode(
                    signer.sign(key,
                        String.format("%s.%s", segments[0], segments[1])));
        }
    }

    /**
     * Nested class for building
     */
    static class EncodeBuilder extends Builder {
        private EncodeBuilder() {
            segments = new String[2];
        }

        EncodeBuilder header(Algorithm algorithm, String type) {
            header = Header.builder()
                    .alg(algorithm.toString())
                    .typ(type)
                    .build();
            return this;
        }

        EncodeBuilder payload(Payload payload) {
            this.payload = payload;
            return this;
        }

        EncodeBuilder encode(Serializer serializer, Base64Encoder encoder) {
            byte[] headerBytes = serializer.serialize(header).getBytes(UTF_8);
            segments[0] = encoder.encode(headerBytes);

            byte[] dataBytes = serializer.serialize(payload).getBytes();
            segments[1] = encoder.encode(dataBytes);

            return this;
        }

        String build(String key, Base64Encoder encoder) {
            sign(key, encoder);
            return String.format("%s.%s.%s", segments[0], segments[1], signature);
        }
    }

    /**
     * Nested class for decoding token and extracting payload
     * @param <T> - payload or class inherited from it
     */
    static class DecodeBuilder<T extends Payload> extends Builder<T> {
        private DecodeBuilder(String[] segments) {
            this.segments = segments;
        }

        private void signer() {
            Algorithm algorithm = Algorithm.valueOf(header.getAlg());
            try {
                signer = (Signer) Class
                        .forName(algorithm.getClassName())
                        .getConstructor(Algorithm.class)
                        .newInstance(algorithm);
            } catch (ClassNotFoundException | NoSuchMethodException |
                    InstantiationException | IllegalAccessException |
                    InvocationTargetException e) {
                throw new SignerCreatingException(e);
            }
        }

        DecodeBuilder decode(Base64Decoder dec, Deserializer des, Class<T> cl) {
            header = des.deserialize(dec.decode(segments[0]), Header.class);
            payload = des.deserialize(dec.decode(segments[1]), cl);
            signer();
            return this;
        }

        DecodeBuilder verify(String key, Base64Encoder encoder) {
            sign(key, encoder);
            if (!signature.equals(segments[2])) {
                throw new VerificationException("Signature verification failed");
            }
            return this;
        }

        T build() {
            return payload;
        }
    }
}