package dev.earlspilner.auth.service;

import dev.earlspilner.auth.dto.AuthDto;
import dev.earlspilner.auth.dto.Tokens;

/**
 * @author Alexander Dudkin
 */
public interface AuthenticationServer {
    Tokens authenticate(AuthDto authDto);
    Tokens refresh(String refreshToken);
}
