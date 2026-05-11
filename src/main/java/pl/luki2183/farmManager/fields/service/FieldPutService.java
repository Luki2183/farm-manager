package pl.luki2183.farmManager.fields.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.luki2183.farmManager.exception.model.FieldEntityNotFoundException;
import pl.luki2183.farmManager.fields.dto.FieldDto;
import pl.luki2183.farmManager.fields.dto.GeoJSONDto;
import pl.luki2183.farmManager.fields.model.FieldEntity;
import pl.luki2183.farmManager.fields.repo.FieldRepository;
import pl.luki2183.farmManager.fields.utils.FieldUpdateHelper;

import java.util.Optional;

/**
 * Service class responsible for updating existing farm fields.
 */
@Slf4j
@Service
@AllArgsConstructor
public class FieldPutService {

    private final FieldRepository repository;
    private final FieldUpdateHelper helper;

    /**
     * Updates the geometry of the field identified by {@code fieldId}
     * with the coordinates provided in {@code dto}.
     *
     * @param fieldId the business identifier of the field to update
     * @param dto     the {@link GeoJSONDto} containing the updated geometry
     * @return the updated field as a {@link FieldDto}
     * @throws pl.luki2183.farmManager.exception.model.FieldEntityNotFoundException
     *         if no field with the given ID exists
     */
    @Transactional
    public FieldDto updateField(String fieldId, GeoJSONDto dto) {
        log.info("Updating FieldEntity with id: {}, and data: {}", fieldId, dto);
        Optional<FieldEntity> existingEntity = repository.findByFieldId(fieldId);
        if (existingEntity.isEmpty()) {
            log.warn("Did not find field with id: {}", fieldId);
            throw new FieldEntityNotFoundException();
        }
        FieldDto result = helper.update(existingEntity.get(), dto);
        log.debug("Updated FieldEntity result: {}", result);
        return result;
    }
}
