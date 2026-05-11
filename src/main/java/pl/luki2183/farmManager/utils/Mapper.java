package pl.luki2183.farmManager.utils;

/**
 * Generic interface for bidirectional mapping between a JPA entity and its DTO.
 *
 * @param <E> entity type
 * @param <D> DTO type
 */
public interface Mapper <E, D>{
    /**
     * Converts a DTO into its corresponding entity representation.
     *
     * @param dto source DTO
     * @return mapped entity
     */
    E fromDtoToEntity(D dto);
    /**
     * Converts an entity into its corresponding DTO representation.
     *
     * @param entity source entity
     * @return mapped DTO
     */
    D fromEntityToDto(E entity);
}
