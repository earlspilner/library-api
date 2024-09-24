package dev.earlspilner.auth.rest.controller;

import dev.earlspilner.auth.dto.AuthDto;
import dev.earlspilner.auth.dto.Tokens;
import dev.earlspilner.auth.service.AuthenticationServer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Login user and get tokens", description = "Authenticates the user and returns access and refresh tokens.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User logged in successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Tokens.class))),
            @ApiResponse(responseCode = "400", description = "Bad credentials",
                    content = @Content),
    })
    @PostMapping("/login")
    public ResponseEntity<Tokens> login(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Login credentials",
            required = true,
            content = @Content(schema = @Schema(implementation = AuthDto.class)))
                                        @RequestBody AuthDto authDto) {
        return new ResponseEntity<>(authenticationServer.authenticate(authDto), HttpStatus.CREATED);
    }

    @Override
    @Operation(summary = "Refresh tokens", description = "Generates new access and refresh tokens using the provided refresh token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tokens refreshed successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Tokens.class))),
            @ApiResponse(responseCode = "400", description = "Invalid refresh token",
                    content = @Content)
    })
    @PostMapping("/refresh")
    public ResponseEntity<Tokens> refresh(@RequestParam String refreshToken) {
        return new ResponseEntity<>(authenticationServer.refresh(refreshToken), HttpStatus.CREATED);
    }

}
