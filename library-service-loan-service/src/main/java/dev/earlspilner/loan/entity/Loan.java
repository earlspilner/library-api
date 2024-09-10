package dev.earlspilner.loan.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.time.Instant;

import static dev.earlspilner.loan.entity.LoanStatus.ON_LOAN;

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
    @Column(name = "book_id", nullable = false, updatable = false, unique = true)
    private Integer bookId;

    @Setter
    @Column(name = "user_id", nullable = false, updatable = false, unique = true)
    private Integer userId;

    /**
     * Date on which the book was issued.
     */
    @Setter
    @Column(name = "loan_date", nullable = false)
    private Instant loanDate;

    /**
     * Date the book is expected to be returned.
     */
    @Setter
    @Column(name = "return_date", nullable = false)
    private Instant returnDate;

    /**
     * Date of actual return of the book.
     */
    @Setter
    @Column(name = "returned_date")
    private Instant returnedDate;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private LoanStatus status;

    @PrePersist
    protected void onCreate() {
        this.loanDate = Instant.now();
        this.status = ON_LOAN;
    }

    public Loan() {
    }

    public Loan(Integer id, Integer bookId, Integer userId, Instant loanDate, Instant returnDate, Instant returnedDate, LoanStatus status) {
        this.id = id;
        this.bookId = bookId;
        this.userId = userId;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
        this.returnedDate = returnedDate;
        this.status = status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("bookId", bookId)
                .append("userId", userId)
                .append("loanDate", loanDate)
                .append("returnDate", returnDate)
                .append("returnedDate", returnedDate)
                .append("status", status)
                .toString();
    }

}
