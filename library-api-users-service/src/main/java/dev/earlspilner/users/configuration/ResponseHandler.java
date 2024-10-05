package dev.earlspilner.users.configuration;

import dev.earlspilner.users.rest.advice.Failure;
import dev.earlspilner.users.rest.advice.Result;
import dev.earlspilner.users.rest.advice.Success;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @author Alexander Dudkin
 */
@Component
public class ResponseHandler {

    public <T> ResponseEntity<?> handleResult(Result<T> result) {
        if (result instanceof Success<T> success) {
            return new ResponseEntity<>(success.value(), HttpStatus.OK);
        } else if (result instanceof Failure<?> failure) {
            return new ResponseEntity<>(failure.toProblemDetail(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public <T> ResponseEntity<?> handleResult(Result<T> result, HttpStatus httpStatus) {
        if (result instanceof Success<T> success) {
            return new ResponseEntity<>(success.value(), HttpStatus.OK);
        } else if (result instanceof Failure<?> failure) {
            return new ResponseEntity<>(failure.toProblemDetail(), httpStatus);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
