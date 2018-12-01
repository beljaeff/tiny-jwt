package us.cleansite.very.tinyjwt;

public class Jwt {
    private Jwt() {}

    public static Encoder encoder() {
        return new Encoder();
    }

    public static Decoder decoder() {
        return new Decoder();
    }
}
