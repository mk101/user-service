package kolesov.maksim.mapping.user.controller.impl;

import kolesov.maksim.mapping.user.controller.AvatarController;
import kolesov.maksim.mapping.user.dto.ResponseDto;
import kolesov.maksim.mapping.user.exception.ServiceException;
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
    public ResponseDto<Void> setAvatar(UUID userId, MultipartFile file) throws IOException, ServiceException {
        avatarService.updateAvatar(userId, file.getBytes(), FileNameUtils.getExtension(file.getOriginalFilename()));

        return new ResponseDto<>(null);
    }

}
