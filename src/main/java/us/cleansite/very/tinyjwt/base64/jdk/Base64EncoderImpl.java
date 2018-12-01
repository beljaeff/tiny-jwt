package us.cleansite.very.tinyjwt.base64.jdk;

import us.cleansite.very.tinyjwt.base64.Base64Encoder;

public class Base64EncoderImpl implements Base64Encoder {
    @Override
    public String encode(byte[] data) {
        return java.util.Base64
                .getUrlEncoder()
                .encodeToString(data);
    }
}