package pl.luki2183.farmManager.fields.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.luki2183.farmManager.fields.dto.GeoJSONDto;
import pl.luki2183.farmManager.fields.mapper.FieldMapper;
import pl.luki2183.farmManager.fields.repo.FieldRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeoJSONGetService {

    private final FieldRepository repository;
    private final FieldMapper mapper;

    public List<GeoJSONDto> getAllGeoJSONs() {
        return mapper.fieldsToGeoJSONDtoList(repository.findAll());
    }
}
