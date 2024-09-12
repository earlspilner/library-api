package dev.earlspilner.loans.repository;

import dev.earlspilner.loans.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Alexander Dudkin
 */
public interface LoanRepository extends JpaRepository<Loan, Integer> {
}
