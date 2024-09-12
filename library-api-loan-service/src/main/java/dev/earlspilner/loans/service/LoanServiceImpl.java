package dev.earlspilner.loans.service;

import dev.earlspilner.loans.config.LibraryClient;
import dev.earlspilner.loans.config.UserClient;
import dev.earlspilner.loans.dto.BookRecordDto;
import dev.earlspilner.loans.dto.LoanDto;
import dev.earlspilner.loans.dto.UserDto;
import dev.earlspilner.loans.entity.Loan;
import dev.earlspilner.loans.mapper.LoanMapper;
import dev.earlspilner.loans.repository.LoanRepository;
import dev.earlspilner.loans.security.JwtCore;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static dev.earlspilner.loans.dto.BookStatus.ON_LOAN;

/**
 * @author Alexander Dudkin
 */
@Service
public class LoanServiceImpl implements LoanService {

    private final JwtCore jwtCore;
    private final UserClient userClient;
    private final LoanMapper loanMapper;
    private final LibraryClient libraryClient;
    private final LoanRepository loanRepository;

    @Autowired
    public LoanServiceImpl(JwtCore jwtCore, UserClient userClient, LoanMapper loanMapper, LibraryClient libraryClient, LoanRepository loanRepository) {
        this.jwtCore = jwtCore;
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
            throw new UnsupportedOperationException("You can't take this book right now");
        }

        UserDto userDto = userClient.getUser(jwtCore.getUsernameFromToken(jwtCore.getTokenFromRequest(request)));
        libraryClient.setBookOnLoan(dto.bookId(), new BookRecordDto(null, ON_LOAN));
        Loan loan = new Loan(userDto.id(), dto.bookId());
        return loanMapper.toLoanDto(loanRepository.save(loan));
    }

    @Override
    public LoanDto getLoan(Integer loanId) {
        return null;
    }

    @Override
    public LoanDto returnBook(Integer bookId, BookRecordDto dto) {
        return null;
    }
}
