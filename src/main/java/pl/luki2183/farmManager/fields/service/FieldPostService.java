package pl.luki2183.farmManager.fields.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.luki2183.farmManager.exception.model.PrimaryKeyViolationException;
import pl.luki2183.farmManager.fields.dto.FieldDto;
import pl.luki2183.farmManager.fields.dto.GeoJSONDto;
import pl.luki2183.farmManager.fields.model.FieldEntity;
import pl.luki2183.farmManager.fields.mapper.FieldMapper;
import pl.luki2183.farmManager.fields.repo.FieldRepository;
import pl.luki2183.farmManager.fields.utils.FieldFinder;

/**
 * Service class responsible for persisting new farm fields.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FieldPostService {

    private final FieldRepository repository;
    private final FieldMapper mapper;
    private final FieldFinder finder;

    /**
     * Converts the provided {@link GeoJSONDto} to a {@link FieldEntity} and persists it.
     *
     * @param dto the GeoJSON feature describing the new field
     * @return the persisted field as a {@link FieldDto}
     * @throws pl.luki2183.farmManager.exception.model.PrimaryKeyViolationException
     *         if a field with the same ID already exists
     */
    @Transactional
    public FieldDto saveField(GeoJSONDto dto){
        log.info("Creating FieldEntity: {}", dto);
        if (finder.exists(dto.getId())) {
            log.warn("Primary key violation error when creating FieldEntity with id: {}", dto.getId());
            throw new PrimaryKeyViolationException();
        }
        FieldEntity entity = mapper.geoJSONDtoToFieldEntity(dto);
        FieldDto result = mapper.fieldToDto(repository.save(entity));
        log.debug("Created FieldEntity: {}", result);
        return result;
    }
}
