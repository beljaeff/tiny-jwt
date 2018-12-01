package us.cleansite.very.tinyjwt.entity;

import lombok.*;

import java.io.Serializable;

/**
 * Base class for each concrete JWT payload which is
 * the second part of the JWT token.
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Payload implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Identifies the principal that issued the
     * JWT (issuer)
     */
    private String iss;

    /**
     * Identifies the principal that is the subject
     * of the JWT (subject)
     */
    private String sub;

    /**
     * Identifies the recipients that the JWT is
     * intended for (audience)
     */
    private String aud;

    /**
     * Identifies the expiration time on or after
     * which the JWT MUST NOT be accepted for processing
     * (expiration time).
     * This field should use UTC zone.
     */
    private String exp;

    /**
     * Identifies the time before which the JWT
     * MUST NOT be accepted for processing (not before).
     * This field should use UTC zone.
     */
    private String nbf;

    /**
     *  Identifies the time at which the JWT was issued
     *  (issued at).
     */
    private String iat;

    /**
     * Unique identifier for the JWT (JWT ID)
     */
    private String jti;
}