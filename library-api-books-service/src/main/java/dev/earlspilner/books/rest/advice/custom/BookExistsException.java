package dev.earlspilner.books.rest.advice.custom;

public class BookExistsException extends RuntimeException {
    public BookExistsException(String message) {
        super(message);
    }
}
