package dev.earlspilner.books.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * @author Alexander Dudkin
 */
@JsonInclude(NON_NULL)
public record BookRecordDto(
        Integer bookId,
        BookStatus bookStatus
) { }
