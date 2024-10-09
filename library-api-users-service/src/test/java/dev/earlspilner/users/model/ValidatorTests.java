package dev.earlspilner.users.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.List;
import java.util.Locale;
import java.util.Set;

import static dev.earlspilner.users.model.UserRole.ROLE_VISITOR;
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
        LocaleContextHolder.setLocale(Locale.ENGLISH);
        User user = new User();
        user.setName("Joshua Bloch");
        user.setUsername("jbloch");
        user.setEmail("email@email.com");
        user.setPassword("password");
        user.setRoles(List.of(ROLE_VISITOR));

        Validator validator = createValidator();
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

        assertThat(constraintViolations.size()).isZero();
    }

    @Test
    void shouldNotValidateWhenFullNameEmpty() {
        LocaleContextHolder.setLocale(Locale.ENGLISH);
        User user = new User();
        user.setName("");
        user.setUsername("username");
        user.setEmail("email@email.com");
        user.setPassword("password");
        user.setRoles(List.of(ROLE_VISITOR));

        Validator validator = createValidator();
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

        assertThat(constraintViolations.size()).isEqualTo(1);
        ConstraintViolation<User> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("name");
        assertThat(violation.getMessage()).isEqualTo("must not be empty");
    }

    @Test
    void shouldNotValidateWhenUsernameEmpty() {
        LocaleContextHolder.setLocale(Locale.ENGLISH);
        User user = new User();
        user.setName("Brian Goetz");
        user.setUsername("");
        user.setEmail("email@email.com");
        user.setPassword("password");
        user.setRoles(List.of(ROLE_VISITOR));

        Validator validator = createValidator();
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

        assertThat(constraintViolations.size()).isEqualTo(1);
        ConstraintViolation<User> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("username");
        assertThat(violation.getMessage()).isEqualTo("must not be empty");
    }

    @Test
    void shouldNotValidateWhenEmailEmpty() {
        LocaleContextHolder.setLocale(Locale.ENGLISH);
        User user = new User();
        user.setName("James Gosling");
        user.setUsername("username");
        user.setEmail("");
        user.setPassword("password");
        user.setRoles(List.of(ROLE_VISITOR));

        Validator validator = createValidator();
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

        assertThat(constraintViolations.size()).isEqualTo(1);
        ConstraintViolation<User> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("email");
        assertThat(violation.getMessage()).isEqualTo("must not be empty");
    }

    @Test
    void shouldNotValidateWhenPasswordEmpty() {
        LocaleContextHolder.setLocale(Locale.ENGLISH);
        User user = new User();
        user.setName("James Gosling");
        user.setUsername("username");
        user.setEmail("email@email.com");
        user.setPassword("");
        user.setRoles(List.of(ROLE_VISITOR));

        Validator validator = createValidator();
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

        assertThat(constraintViolations.size()).isEqualTo(1);
        ConstraintViolation<User> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("password");
        assertThat(violation.getMessage()).isEqualTo("must not be empty");
    }

}
