package dev.earlspilner.users.service;

import dev.earlspilner.users.dto.UserDto;
import dev.earlspilner.users.mapper.UserMapper;
import dev.earlspilner.users.model.User;
import dev.earlspilner.users.rest.advice.Failure;
import dev.earlspilner.users.rest.advice.Result;
import dev.earlspilner.users.rest.advice.Success;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static dev.earlspilner.users.model.UserRole.ROLE_VISITOR;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Alexander Dudkin
 */
public abstract class AbstractUserServiceTests {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Order(1)
    public void testAddUser_ShouldSuccess() {
        User user = new User();
        user.setName("Alex Tourette");
        user.setUsername("tourette");
        user.setEmail("tourette@gmail.com");
        user.setPassword("password");
        user.setRoles(List.of(ROLE_VISITOR));

        userService.saveUser(userMapper.toUserDto(user));
        assertThat(user.getRoles().parallelStream().allMatch(role -> role.getAuthority().startsWith("ROLE_")), is(true));
        assertThat(user.getUsername(), is("tourette"));
    }

    @Test
    @Order(2)
    public void testAddUser_ShouldReturnFailureWithConflict() {
        User user = new User();
        user.setName("Alex Tourette");
        user.setUsername("tourette");
        user.setEmail("tourette@gmail.com");
        user.setPassword("password");
        user.setRoles(List.of(ROLE_VISITOR));

        Result<UserDto> result = userService.saveUser(userMapper.toUserDto(user));
        assertThat(result instanceof Failure, is(true));

        assertThat(((Failure<UserDto>) result).exMessage().startsWith("User already exists"), is(true));
        assertThat(((Failure<UserDto>) result).httpStatus() == 409, is(true));
    }

    @Test
    @Order(3)
    public void testGetUser_ShouldSuccess() {
        Result<UserDto> result = userService.getUser("jgosling");

        assertThat(result instanceof Success, is(true));
        UserDto userDto = ((Success<UserDto>) result).value();

        assertThat(userDto, is(notNullValue()));
        assertThat(userDto.username(), is("jgosling"));
    }

    @Test
    @Order(4)
    public void testGetUser_ShouldReturnFailureWithNotFound() {
        Result<UserDto> result = userService.getUser("unknown");

        assertThat(result instanceof Failure, is(true));
        assertThat(((Failure<UserDto>) result).exMessage().startsWith("User not found"), is(true));
        assertThat(((Failure<UserDto>) result).httpStatus() == 404, is(true));
    }

    @Test
    @Order(5)
    public void testDeleteUser_ShouldSuccess() {
        userService.deleteUser(0);
        Result<UserDto> result = userService.getUser("jbloch");

        assertThat(result instanceof Failure, is(true));
        assertThat(((Failure<UserDto>) result).exMessage().startsWith("User not found"), is(true));
        assertThat(((Failure<UserDto>) result).httpStatus() == 404, is(true));
    }

}
