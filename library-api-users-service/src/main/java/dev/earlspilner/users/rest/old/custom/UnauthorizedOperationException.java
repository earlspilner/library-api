package dev.earlspilner.users.rest.old.custom;

@Deprecated
public class UnauthorizedOperationException extends RuntimeException {
    public UnauthorizedOperationException(String message) {
        super(message);
    }
}
