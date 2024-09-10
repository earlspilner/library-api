package dev.earlspilner.loan.service;

import dev.earlspilner.loan.dto.BookDto;
import dev.earlspilner.loan.dto.LoanDto;

import java.util.List;

/**
 * @author Alexander Dudkin
 */
public interface LoanService {
    BookDto checkBookAvailability(Integer bookId);
    LoanDto issueBook(LoanDto loanDto);
    LoanDto returnBook(Integer loanId);
    List<LoanDto> getUserLoans(Integer userId);
}
