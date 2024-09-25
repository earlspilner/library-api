package dev.earlspilner.books.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.ISBN;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * @author Alexander Dudkin
 */
@JsonInclude(NON_NULL)
public record BookDto(
        Integer id,
        @ISBN @NotBlank String isbn,
        @NotBlank String title,
        @NotBlank String genre,
        @NotBlank String description,
        @NotBlank String author,
        Boolean isAvailable,
        String appearedUtc
) { }
