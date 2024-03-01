package kolesov.maksim.mapping.user.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "avatar")
@Getter
@Setter
@ToString(exclude = "user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AvatarEntity implements Serializable {

    @Id
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private String filename;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            insertable = false,
            updatable = false
    )
    private UserEntity user;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        AvatarEntity that = (AvatarEntity) o;
        return getUserId() != null && Objects.equals(getUserId(), that.getUserId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

}
