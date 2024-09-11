package dev.earlspilner.library.mapper;

import dev.earlspilner.library.dto.BookRecordDto;
import dev.earlspilner.library.model.BookRecord;
import org.mapstruct.Mapper;

/**
 * @author Alexander Dudkin
 */
@Mapper(componentModel = "spring")
public interface BookRecordMapper {
    BookRecord toEntity(BookRecordDto dto);
    BookRecordDto toDto(BookRecord entity);
}
