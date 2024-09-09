package dev.earlspilner.auth.rest.advice;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class UserExistsException extends RuntimeException {
    public UserExistsException(String username) {
        super("User already exists with username: " + username);
    }
}
