package dev.earlspilner.users.service;

import dev.earlspilner.users.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Alexander Dudkin
 */
public interface UserService {
    void saveUser(User user);
    User getUser(Integer id);
    Page<User> getUsers(Pageable pageable);
    User updateUser(Integer id, User user);
    void deleteUser(Integer id);

    User getUserByUsername(String username);
}
