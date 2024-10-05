package dev.earlspilner.users.service;

import dev.earlspilner.users.dto.UserDto;
import dev.earlspilner.users.mapper.UserMapper;
import dev.earlspilner.users.model.User;
import dev.earlspilner.users.repository.UserRepository;
import dev.earlspilner.users.rest.advice.Failure;
import dev.earlspilner.users.rest.advice.Result;
import dev.earlspilner.users.rest.advice.Success;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Alexander Dudkin
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public Result<UserDto> saveUser(UserDto dto) {
        if (userRepository.existsByUsername(dto.username()))
            return new Failure<>("User already exists with username: " + dto.username(), HttpStatus.CONFLICT.value());

        if (userRepository.existsByEmail(dto.email()))
            return new Failure<>("User already exists with email: " + dto.email(), HttpStatus.CONFLICT.value());

        User user = userMapper.toUserEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.password()));
        userRepository.save(user);

        return new Success<>(userMapper.toRegisterResponse(user));
    }

    @Override
    @Transactional(readOnly = true)
    public Result<UserDto> getUser(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toUserDto)
                .<Result<UserDto>>map(Success::new)
                .orElse(new Failure<>("User not found with username: " + username, HttpStatus.NOT_FOUND.value()));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Result<UserDto>> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toUserDto)
                .map(Success::new);
    }

    @Override
    @Transactional
    public Result<UserDto> updateUser(String username, UserDto dto) {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            return new Failure<>("User not found with username: " + username, HttpStatus.NOT_FOUND.value());
        }

        User user = optionalUser.get();
        String authenticatedUsername = getAuthenticatedUsername();

        if (!authenticatedUsername.equals(username)) {
            return new Failure<>("You are not allowed to update this user.", HttpStatus.FORBIDDEN.value());
        }

        user.setName(dto.name());
        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));
        User savedUser = userRepository.save(user);

        return new Success<>(userMapper.toUserDto(savedUser));
    }

    @Override
    @Transactional
    public Result<Void> deleteUser(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return new Failure<>("User not found with ID: " + id, HttpStatus.NOT_FOUND.value());
        }

        userRepository.delete(optionalUser.get());
        return new Success<>(null);
    }

    private String getAuthenticatedUsername() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getUsername();
    }

}
