package dev.earlspilner.books.repository;

import dev.earlspilner.books.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Alexander Dudkin
 */
public interface BookRepository extends JpaRepository<Book, Integer> {
    Optional<Book> findByIsbn(String isbn);

    boolean existsByIsbn(String isbn);
}
