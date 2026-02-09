package pl.luki2183.farmManager.fieldInfo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.luki2183.farmManager.fieldInfo.dto.FieldInfoDto;
import pl.luki2183.farmManager.fieldInfo.mapper.FieldInfoMapper;
import pl.luki2183.farmManager.fieldInfo.model.FieldInfoEntity;
import pl.luki2183.farmManager.fieldInfo.repo.FieldInfoRepository;

@Service
@AllArgsConstructor
public class FieldInfoPostService {

    private final FieldInfoRepository repository;
    private final FieldInfoMapper mapper;

    public FieldInfoEntity addInfo(FieldInfoDto dto) {
        FieldInfoEntity entity = mapper.dtoToInfo(dto);
        return repository.save(entity);
    }
}
