package us.cleansite.very.tinyjwt.entity;

import lombok.*;

import java.io.Serializable;

/**
 * Represents first part of the JWT token.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Header implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Hashing algorithm being used (HMAC SHA256, RSA or none)
     */
    private String alg;

    /**
     * Type of token which is always JWT :)
     */
    private String typ;
}