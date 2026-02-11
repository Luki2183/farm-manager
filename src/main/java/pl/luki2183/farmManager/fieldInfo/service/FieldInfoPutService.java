package pl.luki2183.farmManager.fieldInfo.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.luki2183.farmManager.fieldInfo.dto.FieldInfoDto;
import pl.luki2183.farmManager.fieldInfo.mapper.FieldInfoMapper;
import pl.luki2183.farmManager.fieldInfo.model.FieldInfoEntity;
import pl.luki2183.farmManager.fieldInfo.repo.FieldInfoRepository;

@Service
@AllArgsConstructor
public class FieldInfoPutService {

    private final FieldInfoRepository repository;
    private final FieldInfoMapper mapper;

    @Transactional
    public FieldInfoEntity updateInfo(FieldInfoDto dto) {
//        todo implement method
        return null;
    }
}
