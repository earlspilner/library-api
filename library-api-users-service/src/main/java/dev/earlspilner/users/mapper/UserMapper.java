package dev.earlspilner.users.mapper;

import dev.earlspilner.users.dto.UserDto;
import dev.earlspilner.users.entity.User;
import org.mapstruct.Mapper;

/**
 * @author Alexander Dudkin
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUserEntity(UserDto userDto);
    UserDto toUserDto(User user);
}
