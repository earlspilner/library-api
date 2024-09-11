package dev.earlspilner.loan.rest.controller;

import dev.earlspilner.loan.dto.BookDto;
import dev.earlspilner.loan.dto.LoanDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * @author Alexander Dudkin
 */
public interface LoanApi {
    ResponseEntity<BookDto> checkBookAvailability(Integer bookId);
    ResponseEntity<LoanDto> issueBook(LoanDto loanDto);
    ResponseEntity<LoanDto> returnBook(Integer loanId);
    ResponseEntity<List<LoanDto>> getUserLoans(Integer userId);
}
