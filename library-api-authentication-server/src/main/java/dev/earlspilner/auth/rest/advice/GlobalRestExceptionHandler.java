package dev.earlspilner.auth.rest.advice;

import dev.earlspilner.auth.rest.advice.custom.BadUserCredentialsException;
import dev.earlspilner.auth.rest.advice.custom.CustomJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

/**
 * @author Alexander Dudkin
 */
@RestControllerAdvice
public class GlobalRestExceptionHandler {

    private static final Map<Class<? extends Exception>, HttpStatus> exceptionStatusMap = new ConcurrentHashMap<>();

    static {
        exceptionStatusMap.put(CustomJwtException.class, UNAUTHORIZED);
        exceptionStatusMap.put(BadUserCredentialsException.class, BAD_REQUEST);
        exceptionStatusMap.put(UsernameNotFoundException.class, NOT_FOUND);
    }

    @ExceptionHandler({
            CustomJwtException.class,
            BadUserCredentialsException.class,
            UsernameNotFoundException.class,
    })
    public ResponseEntity<ProblemDetail> handleException(Exception e) {
        HttpStatus status = exceptionStatusMap.getOrDefault(e.getClass(), INTERNAL_SERVER_ERROR);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, e.getMessage());
        return ResponseEntity.status(status).body(problemDetail);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + " " + fieldError.getDefaultMessage())
                .collect(Collectors.joining("; ", "Validation failed: ", ""));

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(BAD_REQUEST, errorMessage);
        return ResponseEntity.badRequest().body(problemDetail);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleGenericException(Exception e) {
        HttpStatus status = INTERNAL_SERVER_ERROR;
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, "An unexpected error occurred. Please try again later.");
        return ResponseEntity.status(status).body(problemDetail);
    }

}
