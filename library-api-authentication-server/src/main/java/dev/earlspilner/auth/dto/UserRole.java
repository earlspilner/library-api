package dev.earlspilner.auth.dto;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author Alexander Dudkin
 */
public enum UserRole implements GrantedAuthority {
    ROLE_VISITOR, ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
