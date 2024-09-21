package dev.earlspilner.auth.rest.controller;

import dev.earlspilner.auth.dto.AuthDto;
import dev.earlspilner.auth.dto.Tokens;
import dev.earlspilner.auth.service.AuthenticationServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Alexander Dudkin
 */
class AuthenticationServerRestControllerTests {

    private MockMvc mockMvc;

    @Mock
    private AuthenticationServer authenticationServer;

    @InjectMocks
    private AuthenticationServerRestController authenticationServerRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationServerRestController).build();
    }

    @Test
    void login_ShouldReturnTokens_WhenCredentialsAreValid() throws Exception {
        AuthDto authDto = new AuthDto("user", "password");
        Tokens tokens = new Tokens("access-token", "refresh-token");

        when(authenticationServer.authenticate(any(AuthDto.class))).thenReturn(tokens);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "username": "user",
                            "password": "password"
                        }
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accessToken").value("access-token"))
                .andExpect(jsonPath("$.refreshToken").value("refresh-token"));
    }

    @Test
    void refresh_ShouldReturnNewTokens_WhenRefreshTokenIsValid() throws Exception {
        String refreshToken = "valid-refresh-token";
        Tokens tokens = new Tokens("new-access-token", "new-refresh-token");

        when(authenticationServer.refresh(eq(refreshToken))).thenReturn(tokens);

        mockMvc.perform(post("/auth/refresh")
                        .param("refreshToken", refreshToken))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accessToken").value("new-access-token"))
                .andExpect(jsonPath("$.refreshToken").value("new-refresh-token"));
    }

}
