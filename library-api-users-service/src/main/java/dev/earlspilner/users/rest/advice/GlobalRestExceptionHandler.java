package dev.earlspilner.users.rest.advice;

import dev.earlspilner.users.rest.advice.custom.CustomJwtException;
import dev.earlspilner.users.rest.advice.custom.UnauthorizedOperationException;
import dev.earlspilner.users.rest.advice.custom.UserExistsException;
import dev.earlspilner.users.rest.advice.custom.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.http.HttpStatus.*;

/**
 * @author Alexander Dudkin
 */
@RestControllerAdvice
public class GlobalRestExceptionHandler {

    private static final Map<Class<? extends Exception>, HttpStatus> exceptionStatusMap = new ConcurrentHashMap<>();

    static {
        exceptionStatusMap.put(UnauthorizedOperationException.class, UNAUTHORIZED);
        exceptionStatusMap.put(AuthorizationDeniedException.class, FORBIDDEN);
        exceptionStatusMap.put(UserExistsException.class, CONFLICT);
        exceptionStatusMap.put(UserNotFoundException.class, NOT_FOUND);
    }

    @ExceptionHandler({
            UnauthorizedOperationException.class,
            AuthorizationDeniedException.class,
            UserExistsException.class,
            UserNotFoundException.class
    })
    public ResponseEntity<ProblemDetail> handleException(Exception e) {
        HttpStatus status = exceptionStatusMap.getOrDefault(e.getClass(), INTERNAL_SERVER_ERROR);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, e.getMessage());
        return ResponseEntity.status(status).body(problemDetail);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleGenericException(Exception e) {
        HttpStatus status = INTERNAL_SERVER_ERROR;
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, "An unexpected error occurred. Please try again later.");
        return ResponseEntity.status(status).body(problemDetail);
    }

}
