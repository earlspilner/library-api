package dev.earlspilner.users.rest.controller;

import dev.earlspilner.users.configuration.ResponseHandler;
import dev.earlspilner.users.dto.UserDto;
import dev.earlspilner.users.rest.advice.Result;
import dev.earlspilner.users.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author Alexander Dudkin
 */
@RestController
@RequestMapping("/users")
public class UserRestController implements UsersApi {

    private final UserService userService;
    private final ResponseHandler responseHandler;

    @Autowired
    public UserRestController(UserService userService, ResponseHandler responseHandler) {
        this.userService = userService;
        this.responseHandler = responseHandler;
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addUser(@Valid @RequestBody UserDto userDto) {
        Result<UserDto> result = this.userService.saveUser(userDto);
        return responseHandler.handleResult(result);
    }

    @Override
    @GetMapping("/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        Result<UserDto> result = this.userService.getUser(username);
        return responseHandler.handleResult(result, HttpStatus.NOT_FOUND);
    }

    @Override
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<?>> getUsers(Pageable pageable) {
        return new ResponseEntity<>(this.userService.getUsers(pageable), HttpStatus.OK);
    }

    @Override
    @PutMapping("/{username}")
    public ResponseEntity<?> updateUser(@PathVariable String username,
                                        @Valid @RequestBody UserDto userDto) {
        Result<UserDto> result = userService.updateUser(username, userDto);
        return responseHandler.handleResult(result, HttpStatus.NOT_FOUND);
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        Result<Void> result = userService.deleteUser(id);
        return responseHandler.handleResult(result, HttpStatus.NOT_FOUND);
    }

}
