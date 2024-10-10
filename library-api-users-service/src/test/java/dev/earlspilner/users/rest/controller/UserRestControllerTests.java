package dev.earlspilner.users.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.earlspilner.users.mapper.UserMapper;
import dev.earlspilner.users.model.User;
import dev.earlspilner.users.model.UserRole;
import dev.earlspilner.users.service.ApplicationTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Alexander Dudkin
 */
@SpringBootTest
@ContextConfiguration(classes = ApplicationTestConfig.class)
@WebAppConfiguration
class UserRestControllerTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRestController userRestController;

    private MockMvc mockMvc;

    @BeforeEach
    void init() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userRestController).build();
    }

    @Test
    void testCreateUserSuccess() throws Exception {
        User user = new User();
        user.setName("Alex Tourette");
        user.setUsername("tourette");
        user.setEmail("tourette@email.com");
        user.setPassword("password");
        user.setRoles(List.of(UserRole.ROLE_VISITOR));

        ObjectMapper mapper = new ObjectMapper();
        String newUserAsJSON = mapper.writeValueAsString(userMapper.toUserDto(user));
        this.mockMvc.perform(post("/users")
                        .content(newUserAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.username").value("tourette"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetUserSuccess() throws Exception {
        this.mockMvc.perform(get("/users/jbloch")
                        .accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetUserFailure() throws Exception {
        this.mockMvc.perform(get("/users/unknown")
                        .accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreateUserFailureFullName() throws Exception {
        User user = new User();
        user.setName("");
        user.setUsername("tourette");
        user.setEmail("tourette@email.com");
        user.setPassword("password");
        user.setRoles(List.of(UserRole.ROLE_VISITOR));

        ObjectMapper mapper = new ObjectMapper();
        String newUserAsJSON = mapper.writeValueAsString(userMapper.toUserDto(user));
        this.mockMvc.perform(post("/users")
                        .content(newUserAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreateUserFailureUsername() throws Exception {
        User user = new User();
        user.setName("Alex Tourette");
        user.setUsername("");
        user.setEmail("tourette@email.com");
        user.setPassword("password");
        user.setRoles(List.of(UserRole.ROLE_VISITOR));

        ObjectMapper mapper = new ObjectMapper();
        String newUserAsJSON = mapper.writeValueAsString(userMapper.toUserDto(user));
        this.mockMvc.perform(post("/users")
                        .content(newUserAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreateUserFailureEmail() throws Exception {
        User user = new User();
        user.setName("Alex Tourette");
        user.setUsername("tourette");
        user.setEmail("");
        user.setPassword("password");
        user.setRoles(List.of(UserRole.ROLE_VISITOR));

        ObjectMapper mapper = new ObjectMapper();
        String newUserAsJSON = mapper.writeValueAsString(userMapper.toUserDto(user));
        this.mockMvc.perform(post("/users")
                        .content(newUserAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreateUserFailurePassword() throws Exception {
        User user = new User();
        user.setName("Alex Tourette");
        user.setUsername("tourette");
        user.setEmail("tourette@email.com");
        user.setPassword("");
        user.setRoles(List.of(UserRole.ROLE_VISITOR));

        ObjectMapper mapper = new ObjectMapper();
        String newUserAsJSON = mapper.writeValueAsString(userMapper.toUserDto(user));
        this.mockMvc.perform(post("/users")
                        .content(newUserAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

}
