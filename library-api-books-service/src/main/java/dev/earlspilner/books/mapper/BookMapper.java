package dev.earlspilner.books.mapper;

import dev.earlspilner.books.dto.BookDto;
import dev.earlspilner.books.model.Book;
import org.mapstruct.Mapper;

/**
 * @author Alexander Dudkin
 */
@Mapper(componentModel = "spring")
public interface BookMapper {
    Book toBookEntity(BookDto dto);
    BookDto toBookDto(Book book);
}
