package dev.earlspilner.auth.security;

import dev.earlspilner.auth.dto.UserDto;
import dev.earlspilner.auth.feign.UserServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @author Alexander Dudkin
 */
@Component
public class CustomUserDetails implements UserDetailsService {

    private final UserServiceClient feignClient;

    @Autowired
    public CustomUserDetails(UserServiceClient feignClient) {
        this.feignClient = feignClient;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto dto = feignClient.getUser(username);
        if (dto == null)
            throw new UsernameNotFoundException(username);

        return User.withUsername(username)
                .password(dto.password())
                .authorities(dto.roles())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }

}
