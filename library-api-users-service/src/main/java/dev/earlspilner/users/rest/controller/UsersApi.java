package dev.earlspilner.users.rest.controller;

import dev.earlspilner.users.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

/**
 * @author Alexander Dudkin
 */
public interface UsersApi {
    ResponseEntity<?> addUser(UserDto userDto);
    ResponseEntity<?> getUser(String username);
    ResponseEntity<Page<?>> getUsers(Pageable pageable);
    ResponseEntity<?> updateUser(String username, UserDto userDto);
    ResponseEntity<?> deleteUser(Integer id);
}
