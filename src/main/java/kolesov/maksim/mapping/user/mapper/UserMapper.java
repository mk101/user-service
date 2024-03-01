package kolesov.maksim.mapping.user.mapper;

import kolesov.maksim.mapping.user.dto.UserDto;
import kolesov.maksim.mapping.user.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {AvatarMapper.class, UserRoleMapper.class}
)
public interface UserMapper extends AbstractMapper<UserEntity, UserDto> {

    @Override
    @Mapping(target = "password", ignore = true)
    UserDto toDto(UserEntity entity);
}
