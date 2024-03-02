package kolesov.maksim.mapping.user.service;

import jakarta.annotation.Nullable;
import kolesov.maksim.mapping.user.exception.ServiceException;

import java.util.UUID;

public interface AvatarService {

    byte[] getUserAvatar(UUID userId) throws ServiceException ;

    void updateAvatar(UUID userId, @Nullable byte[] avatar,  @Nullable String extension) throws ServiceException;

    void deleteAvatar(UUID userId) throws ServiceException;

}
