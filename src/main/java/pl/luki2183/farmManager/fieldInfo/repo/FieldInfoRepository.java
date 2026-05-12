package pl.luki2183.farmManager.fieldInfo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.luki2183.farmManager.fieldInfo.model.FieldInfoEntity;

import java.util.Optional;

/**
 * Spring Data JPA repository for {@link FieldInfoEntity}.
 *
 * <p>Extends {@link JpaRepository} for standard CRUD operations and provides
 * query methods for lookup and existence checks by business field ID.</p>
 */
public interface FieldInfoRepository extends JpaRepository<FieldInfoEntity, String> {
    /**
     * Finds a field info record by its business field identifier.
     *
     * @param fieldId the business identifier of the field
     * @return an {@link Optional} containing the matching {@link FieldInfoEntity},
     *         or empty if none exists
     */
    Optional<FieldInfoEntity> findByFieldId(String fieldId);

    /**
     * Checks whether a field info record with the given business field identifier exists.
     *
     * @param fieldId the business identifier to check
     * @return {@code true} if a matching record exists, {@code false} otherwise
     */
    boolean existsByFieldId(String fieldId);
}
