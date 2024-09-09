package dev.earlspilner.users.rest.controller;

import dev.earlspilner.users.dto.UserDto;
import dev.earlspilner.users.entity.User;
import dev.earlspilner.users.mapper.UserMapper;
import dev.earlspilner.users.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Alexander Dudkin
 */
@RestController
@RequestMapping("/users")
public class UserRestController implements UsersApi {

    private final UserMapper userMapper;
    private final UserService userService;

    @Autowired
    public UserRestController(UserMapper userMapper, UserService userService) {
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @Override
    @PostMapping
    public ResponseEntity<UserDto> addUser(@Valid @RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        userService.saveUser(user);
        return new ResponseEntity<>(userMapper.toDto(user), HttpStatus.CREATED);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Integer id) {
        User user = userService.getUser(id);
        return new ResponseEntity<>(userMapper.toDto(user), HttpStatus.OK);
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<UserDto>> getUsers(Pageable pageable) {
        Page<User> users = userService.getUsers(pageable);
        return new ResponseEntity<>(users.map(userMapper::toDto), HttpStatus.OK);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Integer id, @Valid @RequestBody UserDto userDto) {
        User user = userService.updateUser(id, userMapper.toEntity(userDto));
        return new ResponseEntity<>(userMapper.toDto(user), HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    @GetMapping("/feign/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        return new ResponseEntity<>(userMapper.toDto(user), HttpStatus.OK);
    }

}
