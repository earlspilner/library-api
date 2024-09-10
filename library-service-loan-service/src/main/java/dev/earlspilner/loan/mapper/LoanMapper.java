package dev.earlspilner.loan.mapper;

import dev.earlspilner.loan.dto.LoanDto;
import dev.earlspilner.loan.entity.Loan;
import org.mapstruct.Mapper;

/**
 * @author Alexander Dudkin
 */
@Mapper(componentModel = "spring")
public interface LoanMapper {
    Loan toLoanEntity(LoanDto loanDto);
    LoanDto toLoanDto(Loan loan);
}
