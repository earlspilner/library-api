package dev.earlspilner.loans.rest.controller;

import dev.earlspilner.loans.dto.BookRecordDto;
import dev.earlspilner.loans.dto.LoanDto;
import dev.earlspilner.loans.rest.api.LoanApi;
import dev.earlspilner.loans.service.LoanService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Alexander Dudkin
 */
@RestController
@RequestMapping("/loans")
public class LoanRestController implements LoanApi {

    private final LoanService loanService;

    @Autowired
    public LoanRestController(LoanService loanService) {
        this.loanService = loanService;
    }

    @Override
    @PostMapping
    public ResponseEntity<LoanDto> addLoan(@RequestBody LoanDto dto, HttpServletRequest request) {
        return new ResponseEntity<>(loanService.addLoan(dto, request), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<LoanDto> getLoan(Integer loanId) {
        return null;
    }

    @Override
    public ResponseEntity<LoanDto> returnBook(Integer bookId, BookRecordDto dto) {
        return null;
    }
}
