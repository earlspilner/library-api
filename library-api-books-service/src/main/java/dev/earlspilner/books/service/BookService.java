package dev.earlspilner.books.service;

import dev.earlspilner.books.dto.BookDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Alexander Dudkin
 */
public interface BookService {
    BookDto saveBook(BookDto bookDto);
    BookDto getBook(Integer id);
    BookDto getBookByIsbn(String isbn);
    Page<BookDto> getBooks(Pageable pageable);
    BookDto updateBook(Integer id, BookDto bookDto);
    void deleteBook(Integer id);
}
