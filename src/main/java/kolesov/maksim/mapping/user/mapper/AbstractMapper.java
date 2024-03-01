package kolesov.maksim.mapping.user.mapper;

public interface AbstractMapper<E, D> {

    public abstract E toEntity(D dto);

    public abstract D toDto(E entity);

}
