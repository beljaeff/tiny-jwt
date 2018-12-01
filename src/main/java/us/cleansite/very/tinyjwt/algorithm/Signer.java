package us.cleansite.very.tinyjwt.algorithm;

import lombok.Getter;

/**
 * Base class for all signer algorithms
 */
public abstract class Signer {

    @Getter
    private String algorithm;

    public Signer(Algorithm algorithm) {
        this.algorithm = algorithm.getType();
    }

    public abstract byte[] sign(String secret, String data);
}