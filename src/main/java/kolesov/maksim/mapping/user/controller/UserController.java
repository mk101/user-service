package kolesov.maksim.mapping.user.controller;

import kolesov.maksim.mapping.user.dto.ResponseDto;
import kolesov.maksim.mapping.user.dto.UserDto;
import kolesov.maksim.mapping.user.exception.ServiceException;
import kolesov.maksim.mapping.user.model.UserEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/users")
public interface UserController {

    @GetMapping
    ResponseDto<UserDto> getUser(@RequestParam UUID id);

    @PutMapping
    ResponseDto<UserDto> updateUser(@RequestBody UserDto dto, @AuthenticationPrincipal UserEntity user) throws ServiceException;

}
