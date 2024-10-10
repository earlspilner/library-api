package dev.earlspilner.loans.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Set;

import static java.time.Instant.now;
import static java.time.temporal.ChronoUnit.DAYS;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Alexander Dudkin
 */
class ValidatorTests {

    private Validator createValidator() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        return localValidatorFactoryBean;
    }

    @Test
    void shouldValidate() {
        Loan loan = new Loan();
        loan.setUserId(5);
        loan.setBookId(5);
        loan.setIssuedAt(now());
        loan.setDueTo(now().plus(30, DAYS));

        Validator validator = createValidator();
        Set<ConstraintViolation<Loan>> constraintViolations = validator.validate(loan);

        assertThat(constraintViolations.size()).isZero();
    }

    @Test
    void shouldNotValidateWhenUserIdEmpty() {
        Loan loan = new Loan();
        loan.setBookId(5);
        loan.setIssuedAt(now());
        loan.setDueTo(now().plus(30, DAYS));

        Validator validator = createValidator();
        Set<ConstraintViolation<Loan>> constraintViolations = validator.validate(loan);

        assertThat(constraintViolations.size()).isEqualTo(1);
        ConstraintViolation<Loan> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("userId");
        assertThat(violation.getMessage()).endsWith("null");
    }

    @Test
    void shouldNotValidateWhenBookIdEmpty() {
        Loan loan = new Loan();
        loan.setUserId(5);
        loan.setIssuedAt(now());
        loan.setDueTo(now().plus(30, DAYS));

        Validator validator = createValidator();
        Set<ConstraintViolation<Loan>> constraintViolations = validator.validate(loan);

        assertThat(constraintViolations.size()).isEqualTo(1);
        ConstraintViolation<Loan> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("bookId");
        assertThat(violation.getMessage()).endsWith("null");
    }

    @Test
    void shouldNotValidateWhenIssuedAtEmpty() {
        Loan loan = new Loan();
        loan.setUserId(5);
        loan.setBookId(5);
        loan.setDueTo(now().plus(30, DAYS));

        Validator validator = createValidator();
        Set<ConstraintViolation<Loan>> constraintViolations = validator.validate(loan);

        assertThat(constraintViolations.size()).isEqualTo(1);
        ConstraintViolation<Loan> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("issuedAt");
        assertThat(violation.getMessage()).endsWith("null");
    }

    @Test
    void shouldNotValidateWhenDueToEmpty() {
        Loan loan = new Loan();
        loan.setUserId(5);
        loan.setBookId(5);
        loan.setIssuedAt(now());

        Validator validator = createValidator();
        Set<ConstraintViolation<Loan>> constraintViolations = validator.validate(loan);

        assertThat(constraintViolations.size()).isEqualTo(1);
        ConstraintViolation<Loan> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("dueTo");
        assertThat(violation.getMessage()).endsWith("null");
    }

}
