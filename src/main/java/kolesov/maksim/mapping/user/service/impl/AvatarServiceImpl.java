package kolesov.maksim.mapping.user.service.impl;

import jakarta.annotation.Nullable;
import kolesov.maksim.mapping.user.exception.NotFoundException;
import kolesov.maksim.mapping.user.exception.ServiceException;
import kolesov.maksim.mapping.user.model.AvatarEntity;
import kolesov.maksim.mapping.user.repository.AvatarRepository;
import kolesov.maksim.mapping.user.repository.MinioRepository;
import kolesov.maksim.mapping.user.service.AvatarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AvatarServiceImpl implements AvatarService {

    private final AvatarRepository repository;
    private final MinioRepository minioRepository;

    @Override
    public byte[] getUserAvatar(UUID userId) throws ServiceException {
        Optional<AvatarEntity> entity = repository.findById(userId);
        if (entity.isEmpty()) {
            throw new NotFoundException(String.format("User %s doesn't have avatar", userId));
        }

        return minioRepository.get(entity.get().getFilename());
    }

    @Override
    public void updateAvatar(UUID userId, @Nullable byte[] avatar, @Nullable String extension) throws ServiceException {
        Optional<AvatarEntity> entity = repository.findById(userId);
        if (entity.isEmpty()) {
            if (avatar == null) {
                return;
            }

            String filename = UUID.randomUUID() + "." + extension;
            AvatarEntity avatarEntity = AvatarEntity.builder()
                    .filename(filename)
                    .userId(userId)
            .build();

            minioRepository.upload(filename, avatar);
            repository.save(avatarEntity);
            return;
        }

        if (avatar == null) {
            minioRepository.delete(entity.get().getFilename());
            repository.delete(entity.get());
            return;
        }

        String filename = UUID.randomUUID() + "." + extension;
        minioRepository.delete(entity.get().getFilename());
        minioRepository.upload(filename, avatar);
        entity.get().setFilename(filename);

        repository.save(entity.get());
    }

}
