package dev.earlspilner.library.mapper;

import dev.earlspilner.library.dto.BookRecordDto;
import dev.earlspilner.library.model.BookRecord;
import dev.earlspilner.library.model.BookStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Alexander Dudkin
 */
@Mapper(componentModel = "spring")
public interface BookRecordMapper {

    @Mapping(source = "status", target = "status")
    BookRecordDto toDto(BookRecord entity);

    @Mapping(source = "status", target = "status")
    BookRecord toEntity(BookRecordDto dto);

}
