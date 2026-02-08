package pl.luki2183.farmManager.fields.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.luki2183.farmManager.fields.dto.GeoJSONDto;
import pl.luki2183.farmManager.fields.model.FieldEntity;
import pl.luki2183.farmManager.fields.mapper.FieldMapper;
import pl.luki2183.farmManager.fields.repo.FieldRepository;

@Service
@RequiredArgsConstructor
public class FieldPostService {

    private final FieldRepository repository;
    private final FieldMapper mapper;

    public FieldEntity saveField(GeoJSONDto dto){
        FieldEntity entity = mapper.geoJSONDtoToFieldEntity(dto);
        return repository.save(entity);
    }
}
