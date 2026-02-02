package pl.luki2183.farmManager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.luki2183.farmManager.dto.GeoJSONDto;
import pl.luki2183.farmManager.entity.FieldEntity;
import pl.luki2183.farmManager.mapper.FieldMapper;
import pl.luki2183.farmManager.repo.FieldRepository;

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
