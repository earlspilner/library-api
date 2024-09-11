package dev.earlspilner.books.service;

import dev.earlspilner.books.config.LibraryClient;
import dev.earlspilner.books.dto.BookDto;
import dev.earlspilner.books.dto.BookRecordDto;
import dev.earlspilner.books.dto.BookStatus;
import dev.earlspilner.books.entity.Book;
import dev.earlspilner.books.mapper.BookMapper;
import dev.earlspilner.books.repository.BookRepository;
import dev.earlspilner.books.rest.advice.BookExistsException;
import dev.earlspilner.books.rest.advice.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static dev.earlspilner.books.dto.BookStatus.IN_LIBRARY;

/**
 * @author Alexander Dudkin
 */
@Service
public class BookServiceImpl implements BookService {

    private final BookMapper bookMapper;
    private final LibraryClient libraryClient;
    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookMapper bookMapper, LibraryClient libraryClient, BookRepository bookRepository) {
        this.bookMapper = bookMapper;
        this.libraryClient = libraryClient;
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional
    public BookDto saveBook(BookDto bookDto) {
        if (bookRepository.existsByIsbn(bookDto.isbn()))
            throw new BookExistsException("Book already exists with ISBN: " + bookDto.isbn());

        Book book = bookRepository.save(bookMapper.toBookEntity(bookDto));
        libraryClient.addBookRecord(new BookRecordDto(book.getId(), IN_LIBRARY));
        return bookMapper.toBookDto(book);
    }

    @Override
    @Transactional(readOnly = true)
    public BookDto getBook(Integer id) {
        return bookRepository.findById(id)
                .map(bookMapper::toBookDto)
                .orElseThrow(() -> new BookNotFoundException("Book not found with ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public BookDto getBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .map(bookMapper::toBookDto)
                .orElseThrow(() -> new BookNotFoundException("Book not found with ISBN: " + isbn));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookDto> getBooks(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(bookMapper::toBookDto);
    }

    @Override
    @Transactional
    public BookDto updateBook(Integer id, BookDto bookDto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with ID: " + id));

        book.setIsbn(bookDto.isbn());
        book.setTitle(bookDto.title());
        book.setGenre(bookDto.genre());
        book.setDescription(bookDto.description());
        book.setAuthor(bookDto.author());

        return bookMapper.toBookDto(bookRepository.save(book));
    }

    @Override
    @Transactional
    public void deleteBook(Integer bookId) {
        bookRepository.deleteById(bookId);
        libraryClient.deleteBookRecord(bookId);
    }
}
