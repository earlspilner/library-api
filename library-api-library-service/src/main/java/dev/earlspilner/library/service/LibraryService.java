package dev.earlspilner.library.service;

import dev.earlspilner.library.dto.BookRecordDto;

/**
 * @author Alexander Dudkin
 */
public interface LibraryService {
    BookRecordDto addBookRecord(BookRecordDto dto);
    BookRecordDto getBookRecord(Integer id);
    BookRecordDto updateBookRecord(Integer bookId, BookRecordDto dto);
}
