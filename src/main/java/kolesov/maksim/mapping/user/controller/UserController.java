package kolesov.maksim.mapping.user.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
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
    @Tags(value = {@Tag(name = "UserService")})
    ResponseDto<UserDto> getUser(@RequestParam UUID id);

    @PutMapping
    @Tags(value = {@Tag(name = "UserService")})
    ResponseDto<UserDto> updateUser(@RequestBody @Valid UserDto dto, @AuthenticationPrincipal UserEntity user) throws ServiceException;

}
