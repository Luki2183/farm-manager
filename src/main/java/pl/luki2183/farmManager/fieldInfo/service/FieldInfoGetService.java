package pl.luki2183.farmManager.fieldInfo.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.luki2183.farmManager.fieldInfo.dto.FieldInfoDto;
import pl.luki2183.farmManager.fieldInfo.dto.FieldInfoListDto;
import pl.luki2183.farmManager.fieldInfo.mapper.FieldInfoMapper;
import pl.luki2183.farmManager.fieldInfo.model.FieldInfoEntity;
import pl.luki2183.farmManager.fieldInfo.model.Grain;
import pl.luki2183.farmManager.fieldInfo.repo.FieldInfoRepository;
import pl.luki2183.farmManager.fieldInfo.utils.FieldInfoFinder;

import java.util.List;

/**
 * Service class responsible for retrieving field info records.
 *
 * <p>Uses {@link FieldInfoFinder} for single-record lookups, delegating
 * find-or-throw logic out of this service.</p>
 */
@Slf4j
@Service
@AllArgsConstructor
public class FieldInfoGetService {

    private final FieldInfoRepository repository;
    private final FieldInfoMapper mapper;
    private final FieldInfoFinder finder;

    /**
     * Retrieves all field info records as a {@link FieldInfoListDto}.
     *
     * @return a {@link FieldInfoListDto} containing all persisted field infos and their count
     */
    public FieldInfoListDto getAllInfo() {
        log.info("Fetching all fields");
        FieldInfoListDto result = mapper.infoToDtoList(repository.findAll());
        log.debug("Retrieved all fields result: {}", result);
        return result;
    }

    /**
     * Retrieves a single field info record by its business field identifier.
     *
     * @param fieldId the business identifier of the field
     * @return the matching {@link FieldInfoDto}
     * @throws pl.luki2183.farmManager.exception.model.FieldInfoEntityNotFoundException
     *         if no field info with the given ID exists, thrown by
     *         {@link FieldInfoFinder#find(String)}
     */
    public FieldInfoDto getInfoByFieldId(String fieldId) {
        log.info("Fetching field info with id: {}", fieldId);
        FieldInfoEntity entity = finder.find(fieldId);
        FieldInfoDto result = mapper.infoToDto(entity);
        log.debug("Retrieved field info: {}", result);
        return result;
    }

    /**
     * Retrieves a filtered list of field info records based on the provided criteria.
     * All parameters are optional; passing {@code null} for any filter disables that filter.
     *
     * @param name      optional case-insensitive partial match on the field name
     * @param grainType optional exact match on grain type; {@link Grain#DEFAULT} matches all types
     * @param minArea   optional minimum surface area in square meters (inclusive)
     * @param maxArea   optional maximum surface area in square meters (inclusive)
     * @param humidity  optional minimum humidity filter as a percentage
     * @param wind      optional maximum wind speed filter in km/h
     * @return a {@link FieldInfoListDto} containing the filtered results and their count
     */
    public FieldInfoListDto getFilteredFields(
            String name,
            Grain grainType,
            Double minArea,
            Double maxArea,
            Double humidity,
            Double wind
    ) {
        log.info("Fetching all field info with filters: name={}, grainType={}, minArea={}, maxArea={}, humidity={}, wind={}", name, grainType, minArea, maxArea, humidity, wind);
        List<FieldInfoEntity> entities = repository.findAll().stream()
                .filter(entity -> grainType == null ||  entity.getGrainType().equals(grainType) || grainType.equals(Grain.DEFAULT))
                .filter(entity -> minArea == null || (entity.getSurfaceArea() >= minArea))
                .filter(entity -> maxArea == null || (entity.getSurfaceArea() <= maxArea))
                .filter(entity -> humidity == null || (entity.getSurfaceArea() >= humidity))
                .filter(entity -> wind == null || (entity.getSurfaceArea() <= wind))
                .filter(entity -> name == null || name.isBlank() || (entity.getFieldName().toLowerCase().contains(name.toLowerCase())))
                .toList();
        FieldInfoListDto result = mapper.infoToDtoList(entities);
        log.debug("Retrieved all fields result: {}", result);
        return result;
    }
}
