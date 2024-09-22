package dev.earlspilner.library.rest.advice;

import dev.earlspilner.library.rest.advice.custom.BookRecordNotFoundException;
import dev.earlspilner.library.rest.advice.custom.UnauthorizedOperationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.*;

/**
 * @author Alexander Dudkin
 */
@ControllerAdvice
public class RestControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleException(Exception e) {
        ProblemDetail problemDetail = createProblemDetail(INTERNAL_SERVER_ERROR, e.getMessage());
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(problemDetail);
    }

    @ExceptionHandler(UnauthorizedOperationException.class)
    public ResponseEntity<ProblemDetail> handleUnauthorizedOperationException(UnauthorizedOperationException e) {
        ProblemDetail problemDetail = createProblemDetail(HttpStatus.UNAUTHORIZED, e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(problemDetail);
    }

    @ExceptionHandler(BookRecordNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleBookRecordNotFoundException(BookRecordNotFoundException e) {
        ProblemDetail problemDetail = createProblemDetail(NOT_FOUND, e.getMessage());
        return ResponseEntity.status(NOT_FOUND).body(problemDetail);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ProblemDetail> handleIllegalArgumentException(IllegalArgumentException e) {
        ProblemDetail problemDetail = createProblemDetail(BAD_REQUEST, e.getMessage());
        return ResponseEntity.status(BAD_REQUEST).body(problemDetail);
    }

    private ProblemDetail createProblemDetail(HttpStatus status, String title) {
        return ProblemDetail.forStatusAndDetail(status, title);
    }

}
