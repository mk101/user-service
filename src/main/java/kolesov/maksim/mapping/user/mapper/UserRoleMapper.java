package kolesov.maksim.mapping.user.mapper;

import kolesov.maksim.mapping.user.dto.UserRoleDto;
import kolesov.maksim.mapping.user.model.UserRoleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRoleMapper extends AbstractMapper<UserRoleEntity, UserRoleDto> {
}
