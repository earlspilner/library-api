package dev.earlspilner.books.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * @author Alexander Dudkin
 */
@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    private final JwtCore jwtCore;

    @Autowired
    public AuthenticationFilter(JwtCore jwtCore) {
        this.jwtCore = jwtCore;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(AUTHORIZATION);

        if (isWhiteRequest(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (header == null || !header.startsWith("Bearer ")) {
            response.setStatus(SC_UNAUTHORIZED);
            return;
        }

        String token = header.substring(7);
        if (jwtCore.isInvalid(token)) {
            response.setStatus(SC_UNAUTHORIZED);
            return;
        }

        String username = jwtCore.getUsernameFromToken(token);
        List<String> roles = jwtCore.getRolesFromToken(token);

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            List<SimpleGrantedAuthority> authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .toList();

            User principal = new User(username, "", authorities);
            PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(principal, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private boolean isWhiteRequest(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        String requestURI = request.getRequestURI();

        return (userAgent != null && userAgent.contains("Feign"));
    }

}
