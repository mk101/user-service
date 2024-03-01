package kolesov.maksim.mapping.user.mapper;

import kolesov.maksim.mapping.user.dto.AvatarDto;
import kolesov.maksim.mapping.user.model.AvatarEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AvatarMapper extends AbstractMapper<AvatarEntity, AvatarDto> {
}
