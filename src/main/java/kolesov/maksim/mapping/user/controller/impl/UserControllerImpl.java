package kolesov.maksim.mapping.user.controller.impl;

import kolesov.maksim.mapping.user.controller.UserController;
import kolesov.maksim.mapping.user.dto.ResponseDto;
import kolesov.maksim.mapping.user.dto.UserDto;
import kolesov.maksim.mapping.user.exception.NotFoundException;
import kolesov.maksim.mapping.user.exception.ServiceException;
import kolesov.maksim.mapping.user.mapper.UserMapper;
import kolesov.maksim.mapping.user.model.UserEntity;
import kolesov.maksim.mapping.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Override
    public ResponseDto<UserDto> getUser(UUID id) {
        Optional<UserEntity> user = userService.findById(id);
        if (user.isEmpty()) {
            throw new NotFoundException("User not found");
        }

        return new ResponseDto<>(userMapper.toDto(user.get()));
    }

    @Override
    public ResponseDto<UserDto> updateUser(UserDto dto, UserEntity user) throws ServiceException {
        userService.updateUser(dto, user.getId());

        return new ResponseDto<>(dto);
    }

}
