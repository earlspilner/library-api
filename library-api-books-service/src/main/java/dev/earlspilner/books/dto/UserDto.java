package dev.earlspilner.books.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * @author Alexander Dudkin
 */
@JsonInclude(NON_NULL)
public record UserDto(
        Integer id,
        @NotNull String name,
        @NotNull String username,
        @Email @NotNull String email,
        @NotNull String password,
        String createdUtc,
        String updatedUtc,
        List<UserRole> roles
) { }
