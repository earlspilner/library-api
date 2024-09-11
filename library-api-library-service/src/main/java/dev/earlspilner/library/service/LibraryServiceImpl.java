package dev.earlspilner.library.service;

import dev.earlspilner.library.dto.BookRecordDto;
import dev.earlspilner.library.mapper.BookRecordMapper;
import dev.earlspilner.library.model.BookRecord;
import dev.earlspilner.library.repository.BookRecordRepository;
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
    public BookRecordDto getBookRecord(Integer id) {
        return null;
    }

    @Override
    public BookRecordDto updateBookRecord(Integer bookId, BookRecordDto dto) {
        return null;
    }
}
