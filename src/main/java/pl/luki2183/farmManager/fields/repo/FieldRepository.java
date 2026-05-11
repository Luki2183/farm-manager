package pl.luki2183.farmManager.fields.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.luki2183.farmManager.fields.model.FieldEntity;

import java.util.Optional;

/**
 * Spring Data JPA repository for {@link FieldEntity}.
 *
 * <p>Extends {@link JpaRepository} for standard CRUD operations and provides
 * query methods for lookup and existence checks by business field ID.</p>
 */
public interface FieldRepository extends JpaRepository<FieldEntity, String>{
    /**
     * Finds a field by its business identifier.
     *
     * @param fieldId the business identifier of the field
     * @return an {@link Optional} containing the matching {@link FieldEntity},
     *         or empty if none exists
     */
    Optional<FieldEntity> findByFieldId(String fieldId);

    /**
     * Checks whether a field with the given business identifier exists.
     *
     * @param id the business identifier to check
     * @return {@code true} if a field with the given ID exists, {@code false} otherwise
     */
    boolean existsByFieldId(String id);
}
