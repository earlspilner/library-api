package dev.earlspilner.gateway.advice;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

/**
 * @author Alexander Dudkin
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExpiredJwtException.class)
    public Mono<ResponseEntity<ProblemDetail>> handleExpiredJwtException(ExpiredJwtException ex, ServerWebExchange exchange) {
        String errorMessage = "JWT token has expired. Please login again.";

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(UNAUTHORIZED, errorMessage);
        return Mono.just(ResponseEntity.status(UNAUTHORIZED).body(problemDetail));
    }

}
