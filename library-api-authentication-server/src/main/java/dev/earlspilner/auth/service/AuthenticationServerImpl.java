package dev.earlspilner.auth.service;

import dev.earlspilner.auth.dto.AuthDto;
import dev.earlspilner.auth.dto.Tokens;
import dev.earlspilner.auth.dto.UserDto;
import dev.earlspilner.auth.feign.UserServiceClient;
import dev.earlspilner.auth.rest.advice.BadUserCredentialsException;
import dev.earlspilner.auth.security.JwtCore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Alexander Dudkin
 */
@Service
public class AuthenticationServerImpl implements AuthenticationServer {

    private final JwtCore jwtCore;
    private final UserServiceClient feignClient;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationServerImpl(JwtCore jwtCore,
                                    UserServiceClient feignClient,
                                    PasswordEncoder passwordEncoder,
                                    AuthenticationManager authenticationManager) {
        this.jwtCore = jwtCore;
        this.feignClient = feignClient;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Tokens authenticate(AuthDto authDto) {
        try {
            UserDto user = feignClient.getUser(authDto.username());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authDto.username(), authDto.password()));
            return jwtCore.createTokens(user.username(), user.roles());
        } catch (AuthenticationException ex) {
            throw new BadUserCredentialsException(ex.getMessage());
        }
    }

    @Override
    public Tokens refresh(String refreshToken) {
        String username = jwtCore.getUsername(refreshToken);
        UserDto user = feignClient.getUser(username);
        if (user != null) {
            return jwtCore.createTokens(user.username(), user.roles());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

}
