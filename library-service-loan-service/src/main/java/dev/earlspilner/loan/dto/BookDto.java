package dev.earlspilner.loan.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * @author Alexander Dudkin
 */
@JsonInclude(NON_NULL)
public record BookDto(
        Integer id,
        @NotNull String isbn,
        @NotNull String title,
        @NotNull String genre,
        @NotNull String description,
        @NotNull String author,
        Boolean isAvailable,
        String appearedUtc
) { }
