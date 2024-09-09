package dev.earlspilner.auth.rest.controller;

import dev.earlspilner.auth.dto.AuthDto;
import dev.earlspilner.auth.dto.Tokens;
import dev.earlspilner.auth.service.AuthenticationServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Alexander Dudkin
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationServerRestController implements AuthenticationServerApi {

    private final AuthenticationServer authenticationServer;

    @Autowired
    public AuthenticationServerRestController(AuthenticationServer authenticationServer) {
        this.authenticationServer = authenticationServer;
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<Tokens> login(@RequestBody AuthDto authDto) {
        return new ResponseEntity<>(authenticationServer.authenticate(authDto), HttpStatus.CREATED);
    }

    @Override
    @PostMapping("/refresh")
    public ResponseEntity<Tokens> refresh(@RequestParam String refreshToken) {
        return new ResponseEntity<>(authenticationServer.refresh(refreshToken), HttpStatus.CREATED);
    }

}
