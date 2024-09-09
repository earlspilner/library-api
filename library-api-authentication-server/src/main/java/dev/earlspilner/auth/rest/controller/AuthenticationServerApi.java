package dev.earlspilner.auth.rest.controller;

import dev.earlspilner.auth.dto.AuthDto;
import dev.earlspilner.auth.dto.Tokens;
import org.springframework.http.ResponseEntity;

/**
 * @author Alexander Dudkin
 */
public interface AuthenticationServerApi {
    ResponseEntity<Tokens> login(AuthDto authDto);
    ResponseEntity<Tokens> refresh(String refreshToken);
}
