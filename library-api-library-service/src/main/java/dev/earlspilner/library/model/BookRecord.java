package dev.earlspilner.library.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.time.Instant;

import static dev.earlspilner.library.model.BookStatus.IN_LIBRARY;

/**
 * @author Alexander Dudkin
 */
@Entity @Getter
@Table(name = "library")
public class BookRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    @Column(name = "book_id", nullable = false, updatable = false, unique = true)
    private Integer bookId;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BookStatus status;

    @Setter
    @Column(name = "created_utc", nullable = false, updatable = false)
    private Instant createdUtc;

    @PrePersist
    protected void onCreate() {
        this.createdUtc = Instant.now();
        this.status = IN_LIBRARY;
    }

    public BookRecord() {
    }

    public BookRecord(Integer id, Integer bookId, BookStatus status, Instant createdUtc) {
        this.id = id;
        this.bookId = bookId;
        this.status = status;
        this.createdUtc = createdUtc;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("bookId", bookId)
                .append("status", status)
                .append("createdUtc", createdUtc)
                .toString();
    }

}
