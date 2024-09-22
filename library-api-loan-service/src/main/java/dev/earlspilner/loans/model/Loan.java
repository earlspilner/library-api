package dev.earlspilner.loans.model;

import jakarta.persistence.*;
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
    @Column(name = "userId", nullable = false, updatable = false)
    private Integer userId;

    @Setter
    @Column(name = "bookId", nullable = false, updatable = false)
    private Integer bookId;

    @Setter
    @Column(name = "issuedAt", nullable = false)
    private Instant issuedAt;

    @Setter
    @Column(name = "dueTo", nullable = false)
    private Instant dueTo;

    @Setter
    @Column(name = "returnedAt")
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
