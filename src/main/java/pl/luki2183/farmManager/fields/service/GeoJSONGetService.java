package pl.luki2183.farmManager.fields.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.luki2183.farmManager.fields.dto.GeoJSONListDto;
import pl.luki2183.farmManager.fields.mapper.FieldMapper;
import pl.luki2183.farmManager.fields.repo.FieldRepository;

/**
 * Service class responsible for retrieving all fields in GeoJSON format.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GeoJSONGetService {

    private final FieldRepository repository;
    private final FieldMapper mapper;

    /**
     * Retrieves all fields and returns them as a {@link GeoJSONListDto}.
     *
     * @return a {@link GeoJSONListDto} containing all fields serialized
     *         as GeoJSON features, along with their count
     */
    public GeoJSONListDto getAllGeoJSONs() {
        log.info("Fetching all GeoJSONs");
        GeoJSONListDto result = mapper.fieldsToGeoJSONDtoList(repository.findAll());
        log.debug("Retrieved GeoJSONs: {}", result);
        return result;
    }
}
