package kolesov.maksim.mapping.user.service;

import kolesov.maksim.mapping.user.dto.UserDto;
import kolesov.maksim.mapping.user.exception.ServiceException;
import kolesov.maksim.mapping.user.model.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;
import java.util.UUID;

public interface UserService extends UserDetailsService {

    Optional<UserEntity> findById(UUID id);

    void updateUser(UserDto dto, UUID userId) throws ServiceException;

}
