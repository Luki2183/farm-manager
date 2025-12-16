package pl.luki2183.farmManager.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.luki2183.farmManager.dto.GeoJSONDto;
import pl.luki2183.farmManager.entity.FieldEntity;
import pl.luki2183.farmManager.entity.PointEntity;
import pl.luki2183.farmManager.repo.FieldRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FieldService {

    private final FieldRepository repository;

    public FieldEntity saveField(GeoJSONDto dto){
        FieldEntity entity = new FieldEntity();
//todo refactor to mapper
        entity.setId(dto.getId());

        entity.setCoordinates(
                dto.getGeometry().getCoordinates().getFirst().stream()
                        .map(doubles -> {
                            PointEntity result = new PointEntity();
                            result.setLat(doubles[0]);
                            result.setLng(doubles[1]);
                            return result;
                        }).toList()
        );

        return repository.save(entity);
    }

    public List<FieldEntity> getAllFields(){
        return repository.findAll();
    }

    public void deleteFieldById(String id) {
        repository.deleteById(id);
    }
}
