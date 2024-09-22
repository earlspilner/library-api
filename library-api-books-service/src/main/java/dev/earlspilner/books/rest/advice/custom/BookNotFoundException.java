package dev.earlspilner.books.rest.advice.custom;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String message) {
        super(message);
    }
}
