package dev.earlspilner.users.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author Alexander Dudkin
 */
public enum UserRole implements GrantedAuthority {
    ROLE_USER, ROLE_AUTHOR, ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
