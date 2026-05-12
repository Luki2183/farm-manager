package pl.luki2183.farmManager.fields.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.luki2183.farmManager.fields.dto.FieldDto;
import pl.luki2183.farmManager.fields.dto.FieldListDto;
import pl.luki2183.farmManager.fields.mapper.FieldMapper;
import pl.luki2183.farmManager.fields.model.FieldEntity;
import pl.luki2183.farmManager.fields.repo.FieldRepository;
import pl.luki2183.farmManager.fields.utils.FieldFinder;

/**
 * Service class responsible for retrieving farm fields.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FieldGetService {

    private final FieldRepository repository;
    private final FieldMapper mapper;
    private final FieldFinder finder;

    /**
     * Retrieves all fields as a {@link FieldListDto}.
     *
     * @return a {@link FieldListDto} containing all persisted fields and their count
     */
    public FieldListDto getAllFields(){
        log.info("Fetching all fields");
        FieldListDto result = mapper.fieldsToDtoList(repository.findAll());
        log.debug("Retrieved all fields: {}", result);
        return result;
    }

    /**
     * Retrieves a single field by its business identifier.
     *
     * @param fieldId the business identifier of the field
     * @return the matching {@link FieldDto}
     * @throws pl.luki2183.farmManager.exception.model.FieldEntityNotFoundException
     *         if no field with the given ID exists, thrown by
     *         {@link pl.luki2183.farmManager.fields.utils.FieldFinder#find(String)}
     */
    public FieldDto getFieldWithId(String fieldId) {
        log.info("Fetching field with id: {}", fieldId);
        FieldEntity entity = finder.find(fieldId);
        FieldDto result = mapper.fieldToDto(entity);
        log.debug("Retrieved field: {}", result);
        return result;
    }

    /**
     * Checks whether a field with the given business identifier exists.
     *
     * @param fieldId the business identifier to check
     * @return {@code true} if the field exists, {@code false} otherwise
     */
    public Boolean checkIfExistsByFieldId(String fieldId) {
        log.info("Fetching exists information with id: {}", fieldId);
        Boolean result = finder.exists(fieldId);
        log.debug("Retrieved exist information: {}", result);
        return result;
    }
}
