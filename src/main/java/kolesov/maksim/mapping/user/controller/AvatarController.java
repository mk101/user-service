package kolesov.maksim.mapping.user.controller;

import kolesov.maksim.mapping.user.dto.ResponseDto;
import kolesov.maksim.mapping.user.exception.ServiceException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RequestMapping("/avatars")
public interface AvatarController {

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    ByteArrayResource getAvatar(@PathVariable UUID userId) throws ServiceException;

    @PostMapping(value = "/{userId}")
    ResponseDto<Void> setAvatar(@PathVariable UUID userId, @RequestParam("file") MultipartFile file) throws IOException, ServiceException;

}
