package dev.earlspilner.loans.repository;

import dev.earlspilner.loans.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Alexander Dudkin
 */
public interface LoanRepository extends JpaRepository<Loan, Integer> {
    Optional<Loan> findByBookIdAndUserIdAndReturnedAtIsNull(Integer bookId, Integer userId);
}
