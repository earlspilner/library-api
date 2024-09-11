package dev.earlspilner.loan.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.earlspilner.loan.entity.LoanStatus;
import org.springframework.lang.NonNull;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * @author Alexander Dudkin
 */
@JsonInclude(NON_NULL)
public record LoanDto(
        Integer id,
        @NonNull Integer bookId,
        Integer userId,
        @NonNull String loanDate,
        @NonNull String returnDate,
        String returnedDate,
        LoanStatus status
) { }
