package us.cleansite.very.tinyjwt.exception;

public class TinyJwtException extends RuntimeException {
    public TinyJwtException() {
        super();
    }
    public TinyJwtException(Throwable cause) {
        super(cause);
    }
    public TinyJwtException(String message) {
        super(message);
    }
}