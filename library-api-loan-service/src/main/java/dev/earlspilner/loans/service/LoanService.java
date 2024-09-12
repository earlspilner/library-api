package dev.earlspilner.loans.service;

import dev.earlspilner.loans.dto.BookRecordDto;
import dev.earlspilner.loans.dto.LoanDto;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author Alexander Dudkin
 */
public interface LoanService {
    LoanDto addLoan(LoanDto dto, HttpServletRequest request);
    LoanDto getLoan(Integer loanId);
    LoanDto returnBook(Integer bookId, BookRecordDto dto);
}
