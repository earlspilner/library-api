package dev.earlspilner.library.service;

import dev.earlspilner.library.dto.BookRecordDto;
import dev.earlspilner.library.mapper.BookRecordMapper;
import dev.earlspilner.library.model.BookRecord;
import dev.earlspilner.library.repository.BookRecordRepository;
import dev.earlspilner.library.rest.advice.custom.BookRecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Alexander Dudkin
 */
@Service
public class LibraryServiceImpl implements LibraryService {

    private final BookRecordMapper bookRecordMapper;
    private final BookRecordRepository bookRecordRepository;

    @Autowired
    public LibraryServiceImpl(BookRecordMapper bookRecordMapper, BookRecordRepository bookRecordRepository) {
        this.bookRecordMapper = bookRecordMapper;
        this.bookRecordRepository = bookRecordRepository;
    }

    @Override
    public BookRecordDto addBookRecord(BookRecordDto dto) {
        BookRecord bookRecord = bookRecordMapper.toEntity(dto);
        return bookRecordMapper.toDto(bookRecordRepository.save(bookRecord));
    }

    @Override
    public BookRecordDto getBookRecord(Integer bookId) {
        BookRecord bookRecord = bookRecordRepository.findByBookId(bookId)
                .orElseThrow(() -> new BookRecordNotFoundException("Book not found with ID: " + bookId));
        return bookRecordMapper.toDto(bookRecord);
    }

    @Override
    public BookRecordDto updateBookRecord(Integer bookId, BookRecordDto dto) {
        if (dto.status() == null) {
            throw new IllegalArgumentException("The book status is not set up for this operation.");
        }

        BookRecord bookRecord = bookRecordRepository.findByBookId(bookId)
                .orElseThrow(() -> new BookRecordNotFoundException("Book record not found with ID: " + bookId));

        bookRecord.setStatus(dto.status());
        return bookRecordMapper.toDto(bookRecordRepository.save(bookRecord));
    }

    @Override
    public void deleteBookRecord(Integer bookId) {
        bookRecordRepository.deleteById(bookId);
    }

}
