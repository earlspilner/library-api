package dev.earlspilner.auth.rest.advice;

import dev.earlspilner.auth.rest.advice.custom.BadUserCredentialsException;
import dev.earlspilner.auth.rest.advice.custom.CustomJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @ExceptionHandler(CustomJwtException.class)
    public ResponseEntity<ProblemDetail> handleCustomJwtException(CustomJwtException e) {
        ProblemDetail problemDetail = createProblemDetail(e.getHttpStatus(), e.getMessage());
        return ResponseEntity.status(e.getHttpStatus()).body(problemDetail);
    }

    @ExceptionHandler(BadUserCredentialsException.class)
    public ResponseEntity<ProblemDetail> handleBadUserCredentialsException(BadUserCredentialsException e) {
        ProblemDetail problemDetail = createProblemDetail(BAD_REQUEST, e.getMessage());
        return ResponseEntity.status(BAD_REQUEST).body(problemDetail);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleIllegalArgumentException(IllegalArgumentException e) {
        ProblemDetail problemDetail = createProblemDetail(NOT_FOUND, e.getMessage());
        return ResponseEntity.status(NOT_FOUND).body(problemDetail);
    }

    private ProblemDetail createProblemDetail(HttpStatus status, String title) {
        return ProblemDetail.forStatusAndDetail(status, title);
    }

}
