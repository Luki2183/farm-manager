package pl.luki2183.farmManager.fields.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.luki2183.farmManager.exception.model.FieldEntityNotFoundException;
import pl.luki2183.farmManager.fields.model.FieldEntity;
import pl.luki2183.farmManager.fields.repo.FieldRepository;
import pl.luki2183.farmManager.fields.utils.FieldFinder;

import java.util.Optional;

/**
 * Service class responsible for deleting farm fields.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FieldDeleteService {

    private final FieldRepository repository;
    private final FieldFinder finder;

    /**
     * Deletes the field with the given business identifier.
     *
     * @param fieldId the business identifier of the field to delete
     * @throws pl.luki2183.farmManager.exception.model.FieldEntityNotFoundException
     *         if no field with the given ID exists, thrown by
     *         {@link pl.luki2183.farmManager.fields.utils.FieldFinder#find(String)}
     */
    @Transactional
    public void deleteFieldById(String fieldId) {
        log.info("Deleting FieldEntity with id: {}", fieldId);
        FieldEntity entity = finder.find(fieldId);
        repository.delete(entity);
        log.debug("Successfully deleted FieldEntity with id: {}", fieldId);
    }
}
