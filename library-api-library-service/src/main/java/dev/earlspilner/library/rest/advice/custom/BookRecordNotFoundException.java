package dev.earlspilner.library.rest.advice.custom;

public class BookRecordNotFoundException extends RuntimeException {
    public BookRecordNotFoundException(String message) {
        super(message);
    }
}
