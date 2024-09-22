package dev.earlspilner.loans.mapper;

import dev.earlspilner.loans.dto.LoanDto;
import dev.earlspilner.loans.model.Loan;
import org.mapstruct.Mapper;

/**
 * @author Alexander Dudkin
 */
@Mapper(componentModel = "spring")
public interface LoanMapper {
    LoanDto toLoanDto(Loan loan);
    Loan toLoanEntity(LoanDto loanDto);
}
