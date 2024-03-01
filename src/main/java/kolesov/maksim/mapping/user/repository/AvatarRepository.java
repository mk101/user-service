package kolesov.maksim.mapping.user.repository;

import kolesov.maksim.mapping.user.model.AvatarEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AvatarRepository extends CrudRepository<AvatarEntity, UUID> {
}
