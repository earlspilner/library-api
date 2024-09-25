package dev.earlspilner.loans.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * @author Alexander Dudkin
 */
@JsonInclude(NON_NULL)
public record UserDto(
        Integer id,
        @NotBlank String name,
        @NotBlank String username,
        @Email @NotBlank String email,
        @NotBlank String password,
        String createdUtc,
        String updatedUtc,
        List<UserRole> roles
) { }
