package pl.luki2183.farmManager.fields.utils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.luki2183.farmManager.exception.model.FieldEntityNotFoundException;
import pl.luki2183.farmManager.fields.model.FieldEntity;
import pl.luki2183.farmManager.fields.repo.FieldRepository;
import pl.luki2183.farmManager.utils.Finder;

import java.util.List;
import java.util.Optional;

/**
 * Component implementing {@link pl.luki2183.farmManager.utils.Finder} for
 * {@link FieldEntity}, providing field lookup by business identifier.
 *
 * <p>Intended for use in contexts where a simple find-or-throw pattern is
 * needed without pulling in the full {@link pl.luki2183.farmManager.fields.service.FieldGetService FieldGetService}.</p>
 */
@Slf4j
@Component
@AllArgsConstructor
public class FieldFinder implements Finder<FieldEntity, String> {

    private final FieldRepository repository;

    /**
     * Finds a {@link FieldEntity} by its business identifier.
     *
     * @param fieldId the business identifier of the field
     * @return the matching {@link FieldEntity}
     * @throws pl.luki2183.farmManager.exception.model.FieldEntityNotFoundException
     *         if no field with the given ID exists
     */
    @Override
    public FieldEntity find(String fieldId) {
        log.debug("Entering find with id: {}", fieldId);
        Optional<FieldEntity> fieldEntityOptional = repository.findByFieldId(fieldId);
        if (fieldEntityOptional.isEmpty()) {
            log.warn("Did not find field with id: {}", fieldId);
            throw new FieldEntityNotFoundException();
        }
        FieldEntity result = fieldEntityOptional.get();
        log.debug("Found FieldEntity result: {}", result);
        return result;
    }

    /**
     * Finds all {@link FieldEntity} from repository. Resulting list may be empty.
     * @return List of {@link FieldEntity}
     */
    @Override
    public List<FieldEntity> findAll() {
        log.debug("Entering findAll");
        List<FieldEntity> entities = repository.findAll();
        log.debug("FieldEntity list result: {}", entities);
        return entities;
    }

    /**
     * Checks whether a field with the given business identifier exists.
     *
     * @param fieldId the business identifier of the field to check
     * @return {@code true} if a matching field exists, {@code false} otherwise
     */
    @Override
    public Boolean exists(String fieldId) {
        log.debug("Entering exists with id: {}", fieldId);
        boolean present = repository.existsByFieldId(fieldId);
        log.debug("Status of exists with id: {}, result: {}", fieldId, present);
        return present;
    }
}
