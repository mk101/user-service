package kolesov.maksim.mapping.user.controller;

import kolesov.maksim.mapping.user.dto.ResponseDto;
import kolesov.maksim.mapping.user.exception.ServiceException;
import kolesov.maksim.mapping.user.model.UserEntity;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RequestMapping("/avatars")
public interface AvatarController {

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    ByteArrayResource getAvatar(@PathVariable UUID userId) throws ServiceException;

    @PostMapping(value = "/{userId}")
    ResponseDto<Void> setAvatar(@PathVariable UUID userId, @RequestParam("file") MultipartFile file, @AuthenticationPrincipal UserEntity user) throws IOException, ServiceException;

    @DeleteMapping(value = "/{userId}")
    ResponseDto<Void> deleteAvatar(@PathVariable UUID userId, @AuthenticationPrincipal UserEntity user) throws ServiceException;

}
