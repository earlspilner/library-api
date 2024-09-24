package dev.earlspilner.loans.service;

import dev.earlspilner.loans.feign.LibraryClient;
import dev.earlspilner.loans.feign.UserClient;
import dev.earlspilner.loans.dto.BookRecordDto;
import dev.earlspilner.loans.dto.LoanDto;
import dev.earlspilner.loans.dto.UserDto;
import dev.earlspilner.loans.model.Loan;
import dev.earlspilner.loans.mapper.LoanMapper;
import dev.earlspilner.loans.repository.LoanRepository;
import dev.earlspilner.loans.rest.advice.custom.LoanNotFoundException;
import dev.earlspilner.loans.security.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

import static dev.earlspilner.loans.dto.BookStatus.IN_LIBRARY;
import static dev.earlspilner.loans.dto.BookStatus.ON_LOAN;

/**
 * @author Alexander Dudkin
 */
@Service
public class LoanServiceImpl implements LoanService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserClient userClient;
    private final LoanMapper loanMapper;
    private final LibraryClient libraryClient;
    private final LoanRepository loanRepository;

    @Autowired
    public LoanServiceImpl(JwtTokenProvider jwtTokenProvider, UserClient userClient, LoanMapper loanMapper, LibraryClient libraryClient, LoanRepository loanRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userClient = userClient;
        this.loanMapper = loanMapper;
        this.libraryClient = libraryClient;
        this.loanRepository = loanRepository;
    }

    @Override
    @Transactional
    public LoanDto addLoan(LoanDto dto, HttpServletRequest request) {
        BookRecordDto bookRecord = libraryClient.getBookRecord(dto.bookId());
        if (bookRecord == null || bookRecord.status() == ON_LOAN) {
            throw new UnsupportedOperationException("The book is not available.");
        }

        UserDto userDto = userClient.getUser(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request)));

        libraryClient.setBookStatus(dto.bookId(), new BookRecordDto(null, ON_LOAN));

        Loan loan = new Loan(userDto.id(), dto.bookId());
        return loanMapper.toLoanDto(loanRepository.save(loan));
    }

    @Override
    public LoanDto returnBook(Integer bookId, HttpServletRequest request) {
        BookRecordDto bookRecord = libraryClient.getBookRecord(bookId);
        if (bookRecord == null || bookRecord.status() == IN_LIBRARY) {
            throw new IllegalArgumentException("The book is already in the library.");
        }

        UserDto userDto = userClient.getUser(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request)));

        Loan loan = loanRepository.findByBookIdAndReturnedAtIsNull(bookId)
                .orElseThrow(() -> new LoanNotFoundException("Loan not found for book with ID '" + bookId + "' and user with ID '" + userDto.id() + "'"));

        if (!loan.getUserId().equals(userDto.id())) {
            throw new UnsupportedOperationException("You did not borrow this book, it was borrowed by another user.");
        }

        loan.setReturnedAt(Instant.now());
        libraryClient.setBookStatus(bookId, new BookRecordDto(null, IN_LIBRARY));

        return loanMapper.toLoanDto(loanRepository.save(loan));
    }

    @Override
    public LoanDto getLoan(Integer loanId) {
        return loanRepository.findById(loanId)
                .map(loanMapper::toLoanDto)
                .orElseThrow(() -> new LoanNotFoundException("Loan not found with ID: " + loanId));
    }
}
