package dev.earlspilner.library.rest.controller;

import dev.earlspilner.library.dto.BookRecordDto;
import org.springframework.http.ResponseEntity;

/**
 * @author Alexander Dudkin
 */
public interface LibraryApi {
    ResponseEntity<BookRecordDto> addBootRecord(BookRecordDto dto);
    ResponseEntity<BookRecordDto> getBookRecord(Integer bookId);
    ResponseEntity<BookRecordDto> updateBookRecord(Integer bookId, BookRecordDto dto);
}
