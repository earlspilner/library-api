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
    @GetMapping("/{loanId}")
    public ResponseEntity<LoanDto> getLoan(@PathVariable Integer loanId) {
        return new ResponseEntity<>(loanService.getLoan(loanId), HttpStatus.OK);
    }

    @Override
    @PutMapping("/{loanId}/return")
    public ResponseEntity<LoanDto> returnBook(@PathVariable Integer loanId, HttpServletRequest request) {
        return new ResponseEntity<>(loanService.returnBook(loanId, request), HttpStatus.OK);
    }

}
