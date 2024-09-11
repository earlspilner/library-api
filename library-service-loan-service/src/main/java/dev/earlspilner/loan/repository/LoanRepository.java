package dev.earlspilner.loan.repository;

import dev.earlspilner.loan.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Alexander Dudkin
 */
public interface LoanRepository extends JpaRepository<Loan, Integer> {
    List<Loan> findByUserId(Integer userId);
}
