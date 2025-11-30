package pl.luki2183.farmManager.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.luki2183.farmManager.dto.FieldDto;
import pl.luki2183.farmManager.entity.FieldEntity;
import pl.luki2183.farmManager.entity.PointEntity;
import pl.luki2183.farmManager.repo.FieldRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FieldService {

    private final FieldRepository repository;

    public FieldEntity saveField(FieldDto dto){
        FieldEntity entity = new FieldEntity();
        entity.setName(dto.getName());

        entity.setCoordinates(dto.getCoordinates().stream()
                .map(point -> {
                    PointEntity pointEntity = new PointEntity();
                    pointEntity.setLat(point.getLat());
                    pointEntity.setLng(point.getLng());
                    return pointEntity;
                })
                .collect(Collectors.toList()));

        return repository.save(entity);
    }

    public List<FieldEntity> getAllFields(){
        return repository.findAll();
    }
}
