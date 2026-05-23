package pl.luki2183.farmManager.utils;

import java.util.List;

/**
 * Generic interface for looking up a domain entity by its identifier.
 *
 * @param <T>  the type of entity to return
 * @param <ID> the type of the identifier, extends {@link String}
 */
public interface Finder<T, ID extends String> {
    /**
     * Retrieves an entity by its identifier.
     *
     * @param id the identifier of the entity to find
     * @return the entity associated with the given {@code id}
     * @throws pl.luki2183.farmManager.exception.model.NotFoundException
     *         or equivalent if no entity with the given {@code id} exists
     */
    T find(ID id);
    List<T> findAll();
    Boolean exists(ID id);
}
