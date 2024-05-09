package kolesov.maksim.mapping.user.service.impl;

import kolesov.maksim.mapping.user.dto.UserDto;
import kolesov.maksim.mapping.user.exception.ForbiddenException;
import kolesov.maksim.mapping.user.exception.ServiceException;
import kolesov.maksim.mapping.user.model.Role;
import kolesov.maksim.mapping.user.model.UserEntity;
import kolesov.maksim.mapping.user.model.UserRoleEntity;
import kolesov.maksim.mapping.user.repository.UserRepository;
import kolesov.maksim.mapping.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;

    @Override
    public Optional<UserEntity> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public void updateUser(UserDto dto, UUID userId) throws ServiceException {
        UserEntity old = findById(dto.getId())
                .orElseThrow(() -> new ServiceException("User not found"));

        UserEntity current = findById(userId)
                .orElseThrow(() -> new ServiceException("Current user not found"));
        if (!old.getId().equals(userId) && !current.getRoles().stream().map(UserRoleEntity::getRole).toList().contains(Role.EDIT_USERS)) {
            throw new ForbiddenException("User doesn't have rights");
        }

        if (Boolean.FALSE.equals(old.getActive()) && !current.getRoles().stream().map(UserRoleEntity::getRole).toList().contains(Role.EDIT_USERS)) {
            throw new ForbiddenException("User is disabled");
        }

        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        repository.update(dto.getId(), dto.getFirstName(), dto.getLastName(), old.getPassword(), dto.getActive());
    }

    @Override
    public void resetPassword(UserDto dto, UUID userId) throws ServiceException {
        if (dto.getPassword() == null) {
            throw new ServiceException("Password is null");
        }

        if (!dto.getPassword().matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}$")) {
            throw new ServiceException("Invalid password");
        }

        UserEntity old = findById(dto.getId())
                .orElseThrow(() -> new ServiceException("User not found"));

        UserEntity current = findById(userId)
                .orElseThrow(() -> new ServiceException("Current user not found"));
        if (!old.getId().equals(userId) && !current.getRoles().stream().map(UserRoleEntity::getRole).toList().contains(Role.EDIT_USERS)) {
            throw new ForbiddenException("User doesn't have rights");
        }

        if (Boolean.FALSE.equals(old.getActive()) && !current.getRoles().stream().map(UserRoleEntity::getRole).toList().contains(Role.EDIT_USERS)) {
            throw new ForbiddenException("User is disabled");
        }

        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        repository.update(old.getId(), old.getFirstName(), old.getLastName(), dto.getPassword(), old.getActive());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
