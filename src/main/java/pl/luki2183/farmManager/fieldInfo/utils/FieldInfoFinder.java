package pl.luki2183.farmManager.fieldInfo.utils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.luki2183.farmManager.exception.model.FieldInfoEntityNotFoundException;
import pl.luki2183.farmManager.fieldInfo.model.FieldInfoEntity;
import pl.luki2183.farmManager.fieldInfo.repo.FieldInfoRepository;
import pl.luki2183.farmManager.utils.Finder;

import java.util.Optional;

/**
 * Component implementing {@link pl.luki2183.farmManager.utils.Finder} for
 * {@link FieldInfoEntity}, providing field info lookup by business identifier.
 *
 * <p>Intended for use in contexts where a simple find-or-throw pattern is
 * needed without pulling in the full {@link pl.luki2183.farmManager.fieldInfo.service.FieldInfoGetService FieldInfoGetService}.</p>
 */
@Slf4j
@Component
@AllArgsConstructor
public class FieldInfoFinder implements Finder<FieldInfoEntity, String> {

    private final FieldInfoRepository repository;

    /**
     * Finds a {@link FieldInfoEntity} by its business field identifier.
     *
     * @param fieldId the business identifier of the field
     * @return the matching {@link FieldInfoEntity}
     * @throws pl.luki2183.farmManager.exception.model.FieldInfoEntityNotFoundException
     *         if no field info with the given ID exists
     */
    @Override
    public FieldInfoEntity find(String fieldId) {
        log.debug("Entering find with id: {}", fieldId);
        Optional<FieldInfoEntity> entity = repository.findByFieldId(fieldId);
        if (entity.isEmpty()) {
            log.warn("Did not find field info with id: {}", fieldId);
            throw new FieldInfoEntityNotFoundException();
        }
        FieldInfoEntity result = entity.get();
        log.debug("Found FieldInfoEntity result: {}", result);
        return result;
    }

    /**
     * Checks whether a field info record with the given business identifier exists.
     *
     * @param fieldId the business identifier of the field to check
     * @return {@code true} if a matching record exists, {@code false} otherwise
     */
    @Override
    public Boolean exists(String fieldId) {
        log.debug("Entering exists with id: {}", fieldId);
        boolean present = repository.existsByFieldId(fieldId);
        log.debug("Status of exists with id: {}, result: {}", fieldId, present);
        return present;
    }
}
