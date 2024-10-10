package dev.earlspilner.loans.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * @author Alexander Dudkin
 */
@Entity @Getter
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    @NotNull
    @Column(name = "user_id", nullable = false, updatable = false)
    private Integer userId;

    @Setter
    @NotNull
    @Column(name = "book_id", nullable = false, updatable = false)
    private Integer bookId;

    @Setter
    @NotNull
    @Column(name = "issued_at", nullable = false)
    private Instant issuedAt;

    @Setter
    @NotNull
    @Column(name = "due_to", nullable = false)
    private Instant dueTo;

    @Setter
    @Column(name = "returned_at")
    private Instant returnedAt;

    @PrePersist
    protected void onCreate() {
        this.issuedAt = Instant.now();
        this.dueTo = Instant.now().plus(30, ChronoUnit.DAYS);
    }

    public Loan() {
    }

    public Loan(Integer userId, Integer bookId) {
        this.userId = userId;
        this.bookId = bookId;
    }

    public Loan(Integer id, Integer userId, Integer bookId, Instant issuedAt, Instant dueTo, Instant returnedAt) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.issuedAt = issuedAt;
        this.dueTo = dueTo;
        this.returnedAt = returnedAt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("userId", userId)
                .append("bookId", bookId)
                .append("issuedAt", issuedAt)
                .append("dueTo", dueTo)
                .append("returnedAt", returnedAt)
                .toString();
    }

}
