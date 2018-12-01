package us.cleansite.very.tinyjwt.exception;

public class TokenExpiredException extends TinyJwtException {
    public TokenExpiredException() {
        super();
    }
    public TokenExpiredException(Throwable cause) {
        super(cause);
    }
}