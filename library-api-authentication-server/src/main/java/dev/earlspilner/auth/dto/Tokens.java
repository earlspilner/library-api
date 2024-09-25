package dev.earlspilner.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * @author Alexander Dudkin
 */
@Schema(description = "Access and refresh tokens")
public record Tokens(
        @NotBlank
        @Schema(description = "Access token", example = "eyJhbGciOiJIUzI1...", requiredMode = Schema.RequiredMode.REQUIRED)
        String accessToken,

        @NotBlank
        @Schema(description = "Refresh token", example = "dXNlci1yZWZyZXNoLXRva2Vu..", requiredMode = Schema.RequiredMode.REQUIRED)
        String refreshToken
) { }
