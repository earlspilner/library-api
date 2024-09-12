package dev.earlspilner.loans.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

/**
 * @author Alexander Dudkin
 */
@JsonInclude(NON_NULL)
public record LoanDto(
        @JsonProperty(access = READ_ONLY) Integer id,
        @JsonProperty(access = READ_ONLY) Integer userId,
        Integer bookId,
        @JsonProperty(access = READ_ONLY) String issuedAt,
        @JsonProperty(access = READ_ONLY) String dueTo,
        @JsonProperty(access = READ_ONLY) String returnedAt
) { }
