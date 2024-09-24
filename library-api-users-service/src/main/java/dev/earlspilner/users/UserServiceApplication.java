package dev.earlspilner.users;

import dev.earlspilner.users.mapper.UserMapper;
import dev.earlspilner.users.model.User;
import dev.earlspilner.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

import static dev.earlspilner.users.model.UserRole.ROLE_ADMIN;
import static dev.earlspilner.users.model.UserRole.ROLE_VISITOR;

/**
 * @author Alexander Dudkin
 */
@SpringBootApplication
@RequiredArgsConstructor
public class UserServiceApplication implements CommandLineRunner {

    final UserMapper userMapper;
    final UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        User visitor = new User();
        visitor.setName("visitor");
        visitor.setUsername("visitor");
        visitor.setEmail("visitor@email.com");
        visitor.setPassword("visitor");
        visitor.setRoles(List.of(ROLE_VISITOR));

        userService.saveUser(userMapper.toUserDto(visitor));

        User admin = new User();
        admin.setName("admin");
        admin.setUsername("admin");
        admin.setEmail("admin@email.com");
        admin.setPassword("admin");
        admin.setRoles(List.of(ROLE_ADMIN));

        userService.saveUser(userMapper.toUserDto(admin));
    }

}
