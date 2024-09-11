package dev.earlspilner.library.rest.advice;

public class BookRecordNotFoundException extends RuntimeException {
    public BookRecordNotFoundException(String message) {
        super(message);
    }
}
