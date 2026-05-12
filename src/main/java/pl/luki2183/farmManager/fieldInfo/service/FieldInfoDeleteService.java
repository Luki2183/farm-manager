package pl.luki2183.farmManager.fieldInfo.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.luki2183.farmManager.fieldInfo.model.FieldInfoEntity;
import pl.luki2183.farmManager.fieldInfo.repo.FieldInfoRepository;
import pl.luki2183.farmManager.fieldInfo.utils.FieldInfoFinder;

/**
 * Service class responsible for deleting field info records.
 */
@Slf4j
@Service
@AllArgsConstructor
public class FieldInfoDeleteService {

    private final FieldInfoRepository repository;
    private final FieldInfoFinder finder;

    /**
     * Deletes the field info record with the given business field identifier.
     *
     * @param fieldId the business identifier of the field info to delete
     * @throws pl.luki2183.farmManager.exception.model.FieldInfoEntityNotFoundException
     *         if no field info with the given ID exists, thrown by
     *         {@link FieldInfoFinder#find(String)}
     */
    @Transactional
    public void deleteById(String fieldId) {
        log.info("Deleting FieldInfoEntity with id: {}", fieldId);
        FieldInfoEntity entity = finder.find(fieldId);
        repository.delete(entity);
        log.debug("Successfully deleted FieldInfoEntity with id: {}", fieldId);
    }
}
