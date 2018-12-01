package us.cleansite.very.tinyjwt.base64.jdk;

import us.cleansite.very.tinyjwt.base64.Base64Decoder;

public class Base64DecoderImpl implements Base64Decoder {
    @Override
    public String decode(String encoded) {
        return new String(java.util.Base64
                .getUrlDecoder()
                .decode(encoded));
    }
}