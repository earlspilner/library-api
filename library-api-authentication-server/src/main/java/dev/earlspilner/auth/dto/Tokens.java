package dev.earlspilner.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * @author Alexander Dudkin
 */
@Schema(description = "Access and refresh tokens")
public record Tokens(
        @NotNull
        @Schema(description = "Access token", example = "eyJhbGciOiJIUzI1...", requiredMode = Schema.RequiredMode.REQUIRED)
        String accessToken,

        @NotNull
        @Schema(description = "Refresh token", example = "dXNlci1yZWZyZXNoLXRva2Vu..", requiredMode = Schema.RequiredMode.REQUIRED)
        String refreshToken
) { }
