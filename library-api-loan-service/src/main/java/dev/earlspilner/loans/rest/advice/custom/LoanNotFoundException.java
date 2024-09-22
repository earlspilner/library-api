package dev.earlspilner.loans.rest.advice.custom;

public class LoanNotFoundException extends RuntimeException {
    public LoanNotFoundException(String message) {
        super(message);
    }
}
