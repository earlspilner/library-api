package dev.earlspilner.users.service;

import dev.earlspilner.users.dto.UserDto;
import dev.earlspilner.users.rest.advice.Result;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Alexander Dudkin
 */
public interface UserService {
    Result<UserDto> saveUser(UserDto dto);
    Result<UserDto> getUser(String username);
    Page<Result<UserDto>> getUsers(Pageable pageable);
    Result<UserDto> updateUser(String username, UserDto dto);
    Result<Void> deleteUser(Integer id);
}
