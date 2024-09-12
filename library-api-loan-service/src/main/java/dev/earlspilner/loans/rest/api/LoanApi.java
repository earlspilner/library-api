package dev.earlspilner.loans.rest.api;

import dev.earlspilner.loans.dto.BookRecordDto;
import dev.earlspilner.loans.dto.LoanDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

/**
 * @author Alexander Dudkin
 */
public interface LoanApi {
    ResponseEntity<LoanDto> addLoan(LoanDto dto, HttpServletRequest request);
    ResponseEntity<LoanDto> getLoan(Integer loanId);
    ResponseEntity<LoanDto> returnBook(Integer bookId, BookRecordDto dto);
}
