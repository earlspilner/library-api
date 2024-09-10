package dev.earlspilner.books.rest.advice;

public class BookExistsException extends RuntimeException {
    public BookExistsException(String message) {
        super(message);
    }
}
