package pl.luki2183.farmManager.fields.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.luki2183.farmManager.exception.model.FieldEntityNotFoundException;
import pl.luki2183.farmManager.fields.model.FieldEntity;
import pl.luki2183.farmManager.fields.repo.FieldRepository;

import java.util.Optional;

/**
 * Service class responsible for deleting farm fields.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FieldDeleteService {

    private final FieldRepository repository;

    /**
     * Deletes the field with the given business identifier.
     *
     * @param fieldId the business identifier of the field to delete
     * @throws pl.luki2183.farmManager.exception.model.FieldEntityNotFoundException
     *         if no field with the given ID exists
     */
    @Transactional
    public void deleteFieldById(String fieldId) {
        log.info("Deleting FieldEntity with id: {}", fieldId);
        Optional<FieldEntity> existingEntity = repository.findByFieldId(fieldId);
        if (existingEntity.isEmpty()) {
            log.warn("Did not find field with id: {}", fieldId);
            throw new FieldEntityNotFoundException();
        }
        repository.delete(existingEntity.get());
        log.debug("Successfully deleted FieldEntity with id: {}", fieldId);
    }
}
