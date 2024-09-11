package dev.earlspilner.loan.service;

import dev.earlspilner.loan.dto.BookDto;
import dev.earlspilner.loan.dto.LoanDto;
import dev.earlspilner.loan.entity.Loan;
import dev.earlspilner.loan.feign.BookClient;
import dev.earlspilner.loan.mapper.LoanMapper;
import dev.earlspilner.loan.repository.LoanRepository;
import dev.earlspilner.loan.rest.advice.BookNotAvailableException;
import dev.earlspilner.loan.rest.advice.LoanNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * @author Alexander Dudkin
 */
@Service
public class LoanServiceImpl implements LoanService {

    private final BookClient bookClient;
    private final LoanMapper loanMapper;
    private final LoanRepository loanRepository;

    @Autowired
    public LoanServiceImpl(BookClient bookClient, LoanRepository loanRepository, LoanMapper loanMapper) {
        this.bookClient = bookClient;
        this.loanRepository = loanRepository;
        this.loanMapper = loanMapper;
    }

    @Override
    public BookDto checkBookAvailability(Integer bookId) {
        return bookClient.getBook(bookId);
    }

    @Override
    public LoanDto issueBook(LoanDto loanDto) {
        BookDto book = Optional.ofNullable(bookClient.getBook(loanDto.bookId()))
                .orElseThrow(() -> new BookNotAvailableException("Book not found"));

        if (!book.isAvailable()) {
            throw new BookNotAvailableException("Book is not available");
        }

        Loan loan = loanMapper.toLoanEntity(loanDto);
        loanRepository.save(loan);

        updateBookAvailability(loanDto.bookId(), false);

        return loanMapper.toLoanDto(loan);
    }

    @Override
    public LoanDto returnBook(Integer loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new LoanNotFoundException("Loan not found with ID: " + loanId));

        loan.setReturnedDate(Instant.now());
        loanRepository.save(loan);

        updateBookAvailability(loan.getBookId(), true);

        return loanMapper.toLoanDto(loan);
    }

    @Override
    public List<LoanDto> getUserLoans(Integer userId) {
        return loanRepository.findByUserId(userId).stream()
                .map(loanMapper::toLoanDto)
                .toList();
    }

    @SuppressWarnings("PMD")
    private void updateBookAvailability(Integer bookId, boolean available) {
        BookDto book = bookClient.getBook(bookId);
        bookClient.updateBook(bookId, new BookDto(
                book.id(),
                book.isbn(),
                book.title(),
                book.genre(),
                book.description(),
                book.author(),
                available,
                book.appearedUtc()
        ));
    }
}
