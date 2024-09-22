package dev.earlspilner.books.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.time.Instant;

/**
 * @author Alexander Dudkin
 */
@Entity @Getter
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    @Column(name = "isbn", nullable = false, unique = true)
    private String isbn;

    @Setter
    @Column(name = "title", nullable = false, length = 8192)
    private String title;

    @Setter
    @Column(name = "genre", nullable = false, unique = true)
    private String genre;

    @Setter
    @Column(name = "description", nullable = false, length = 8192)
    private String description;

    @Setter
    @Column(name = "author", nullable = false)
    private String author;

    @Setter
    @Column(name = "appearedUtc", nullable = false, updatable = false)
    private Instant appearedUtc;

    @PrePersist
    protected void onCreate() {
        this.appearedUtc = Instant.now();
    }

    public Book() {
    }

    public Book(Integer id, String isbn, String title, String genre, String description, String author, Instant appearedUtc) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.genre = genre;
        this.description = description;
        this.author = author;
        this.appearedUtc = appearedUtc;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("isbn", isbn)
                .append("title", title)
                .append("genre", genre)
                .append("description", description)
                .append("author", author)
                .append("appearedUtc", appearedUtc)
                .toString();
    }

}
