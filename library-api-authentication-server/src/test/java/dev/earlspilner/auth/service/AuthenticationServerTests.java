package dev.earlspilner.auth.service;

import dev.earlspilner.auth.dto.AuthDto;
import dev.earlspilner.auth.dto.Tokens;
import dev.earlspilner.auth.dto.UserDto;
import dev.earlspilner.auth.feign.UserServiceClient;
import dev.earlspilner.auth.rest.advice.BadUserCredentialsException;
import dev.earlspilner.auth.security.JwtCore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Alexander Dudkin
 */
class AuthenticationServerTests {

    @InjectMocks
    private AuthenticationServerImpl authenticationServer;

    @Mock
    private JwtCore jwtCore;

    @Mock
    private UserServiceClient feignClient;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void authenticate_ShouldReturnTokens_WhenCredentialsAreValid() {
        AuthDto authDto = new AuthDto("user", "password");
        UserDto userDto = new UserDto(1, "User Name", "user", "user@example.com", "encodedPassword", null, null, List.of());
        Tokens tokens = new Tokens("access-token", "refresh-token");

        when(feignClient.getUser(authDto.username())).thenReturn(userDto);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken("user", "password"));
        when(jwtCore.createTokens(userDto.username(), userDto.roles())).thenReturn(tokens);

        Tokens result = authenticationServer.authenticate(authDto);

        verify(feignClient).getUser(authDto.username());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtCore).createTokens(userDto.username(), userDto.roles());
        assertEquals(tokens, result);
    }

    @Test
    void authenticate_ShouldThrowBadUserCredentialsException_WhenAuthenticationFails() {
        AuthDto authDto = new AuthDto("user", "wrongPassword");
        UserDto userDto = new UserDto(1, "User Name", "user", "user@example.com", "encodedPassword", null, null, List.of());
        when(feignClient.getUser(authDto.username())).thenReturn(userDto);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new AuthenticationException("Invalid credentials") {});

        assertThrows(BadUserCredentialsException.class, () -> authenticationServer.authenticate(authDto));
    }

    @Test
    void refresh_ShouldReturnTokens_WhenRefreshTokenIsValid() {
        String refreshToken = "valid-refresh-token";
        String username = "user";
        UserDto userDto = new UserDto(1, "User Name", username, "user@example.com", "encodedPassword", null, null, List.of());
        Tokens tokens = new Tokens("new-access-token", "new-refresh-token");

        when(jwtCore.getUsername(refreshToken)).thenReturn(username);
        when(feignClient.getUser(username)).thenReturn(userDto);
        when(jwtCore.createTokens(userDto.username(), userDto.roles())).thenReturn(tokens);

        Tokens result = authenticationServer.refresh(refreshToken);

        verify(jwtCore).getUsername(refreshToken);
        verify(feignClient).getUser(username);
        verify(jwtCore).createTokens(userDto.username(), userDto.roles());
        assertEquals(tokens, result);
    }

    @Test
    void refresh_ShouldThrowUsernameNotFoundException_WhenUserDoesNotExist() {
        String refreshToken = "invalid-refresh-token";
        String username = "user";

        when(jwtCore.getUsername(refreshToken)).thenReturn(username);
        when(feignClient.getUser(username)).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> authenticationServer.refresh(refreshToken));
    }

}
