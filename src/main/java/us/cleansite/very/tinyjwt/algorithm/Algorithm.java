package us.cleansite.very.tinyjwt.algorithm;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Algorithm {
    HS256("HmacSHA256", "us.cleansite.very.tinyjwt.algorithm.hs.HsSigner"),
    HS384("HmacSHA384", "us.cleansite.very.tinyjwt.algorithm.hs.HsSigner"),
    HS512("HmacSHA512", "us.cleansite.very.tinyjwt.algorithm.hs.HsSigner");

    private String type;
    private String className;
}