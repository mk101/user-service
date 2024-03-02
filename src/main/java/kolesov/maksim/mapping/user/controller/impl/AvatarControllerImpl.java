package kolesov.maksim.mapping.user.controller.impl;

import kolesov.maksim.mapping.user.controller.AvatarController;
import kolesov.maksim.mapping.user.dto.ResponseDto;
import kolesov.maksim.mapping.user.exception.ForbiddenException;
import kolesov.maksim.mapping.user.exception.ServiceException;
import kolesov.maksim.mapping.user.model.Role;
import kolesov.maksim.mapping.user.model.UserEntity;
import kolesov.maksim.mapping.user.model.UserRoleEntity;
import kolesov.maksim.mapping.user.service.AvatarService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.FileNameUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AvatarControllerImpl implements AvatarController {

    private final AvatarService avatarService;

    @Override
    public ByteArrayResource getAvatar(UUID userId) throws ServiceException {
        return new ByteArrayResource(avatarService.getUserAvatar(userId));
    }

    @Override
    public ResponseDto<Void> setAvatar(UUID userId, MultipartFile file, UserEntity user) throws IOException, ServiceException {
        if (!user.getId().equals(userId)) {
            throw new ForbiddenException("Access denied");
        }

        avatarService.updateAvatar(userId, file.getBytes(), FileNameUtils.getExtension(file.getOriginalFilename()));

        return new ResponseDto<>(null);
    }

    @Override
    public ResponseDto<Void> deleteAvatar(UUID userId, UserEntity user) throws ServiceException {
        if (!user.getId().equals(userId) && !user.getRoles().stream().map(UserRoleEntity::getRole).toList().contains(Role.EDIT_USERS)) {
            throw new ForbiddenException("Access denied");
        }

        avatarService.deleteAvatar(userId);

        return new ResponseDto<>(null);
    }

}
