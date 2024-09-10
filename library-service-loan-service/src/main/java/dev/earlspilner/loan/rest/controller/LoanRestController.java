package dev.earlspilner.loan.rest.controller;

import dev.earlspilner.loan.dto.BookDto;
import dev.earlspilner.loan.dto.LoanDto;
import dev.earlspilner.loan.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/books/{bookId}/availability")
    public ResponseEntity<BookDto> checkBookAvailability(@PathVariable Integer bookId) {
        return new ResponseEntity<>(loanService.checkBookAvailability(bookId), HttpStatus.OK);
    }

    @Override
    @PostMapping("/issue")
    public ResponseEntity<LoanDto> issueBook(@RequestBody LoanDto loanDto) {
        return new ResponseEntity<>(loanService.issueBook(loanDto), HttpStatus.CREATED);
    }

    @Override
    @PatchMapping("/return/{loanId}")
    public ResponseEntity<LoanDto> returnBook(@PathVariable Integer loanId) {
        return new ResponseEntity<>(loanService.returnBook(loanId), HttpStatus.OK);
    }

    @Override
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<LoanDto>> getUserLoans(@PathVariable Integer userId) {
        return new ResponseEntity<>(loanService.getUserLoans(userId), HttpStatus.OK);
    }

}
