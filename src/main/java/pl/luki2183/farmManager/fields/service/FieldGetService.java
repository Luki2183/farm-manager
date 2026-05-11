package pl.luki2183.farmManager.fields.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.luki2183.farmManager.exception.model.FieldEntityNotFoundException;
import pl.luki2183.farmManager.fields.dto.FieldDto;
import pl.luki2183.farmManager.fields.dto.FieldListDto;
import pl.luki2183.farmManager.fields.mapper.FieldMapper;
import pl.luki2183.farmManager.fields.model.FieldEntity;
import pl.luki2183.farmManager.fields.repo.FieldRepository;

import java.util.Optional;

/**
 * Service class responsible for retrieving farm fields.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FieldGetService {

    private final FieldRepository repository;
    private final FieldMapper mapper;

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
     *         if no field with the given ID exists
     */
    public FieldDto getFieldWithId(String fieldId) {
        log.info("Fetching field with id: {}", fieldId);
        Optional<FieldEntity> optionalField = repository.findByFieldId(fieldId);
        if (optionalField.isEmpty()) {
            log.warn("Did not find field with id: {}", fieldId);
            throw new FieldEntityNotFoundException();
        }
        FieldDto result = mapper.fieldToDto(optionalField.get());
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
        boolean result = repository.existsByFieldId(fieldId);
        log.debug("Retrieved exist information: {}", result);
        return result;
    }
}
