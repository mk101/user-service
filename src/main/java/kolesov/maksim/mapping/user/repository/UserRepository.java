package kolesov.maksim.mapping.user.repository;

import kolesov.maksim.mapping.user.model.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, UUID> {

    Optional<UserEntity> findByLogin(String login);

    @Query(
            value = "UPDATE \"user\" SET " +
                    "first_name = :firstName, " +
                    "last_name = :lastName, " +
                    "password = :password, " +
                    "active = :active " +
                    "WHERE id = :id",
            nativeQuery = true
    )
    @Modifying(flushAutomatically = true)
    void update(
            @Param("id") UUID id,
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("password") String password,
            @Param("active") Boolean active
    );

}
