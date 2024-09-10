package dev.earlspilner.books.rest.controller;

import dev.earlspilner.books.dto.BookDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

/**
 * @author Alexander Dudkin
 */
public interface BooksApi {
    ResponseEntity<BookDto> addBook(BookDto bookDto);
    ResponseEntity<BookDto> getBook(Integer id);
    ResponseEntity<BookDto> getBookByIsbn(String isbn);
    ResponseEntity<Page<BookDto>> getBooks(Pageable pageable);
    ResponseEntity<BookDto> updateBook(Integer id, BookDto bookDto);
    ResponseEntity<Void> deleteBook(Integer id);
}
