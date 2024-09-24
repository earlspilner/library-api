package dev.earlspilner.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * @author Alexander Dudkin
 */
@Schema(description = "Authentication request data")
public record AuthDto(
        @NotNull
        @Schema(description = "User's username", example = "thisdudkin", requiredMode = Schema.RequiredMode.REQUIRED)
        String username,

        @NotNull
        @Schema(description = "User's password", example = "password", requiredMode = Schema.RequiredMode.REQUIRED)
        String password
) { }
