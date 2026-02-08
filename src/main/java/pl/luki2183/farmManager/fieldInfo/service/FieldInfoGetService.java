package pl.luki2183.farmManager.fieldInfo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.luki2183.farmManager.fieldInfo.dto.FieldInfoDto;
import pl.luki2183.farmManager.fieldInfo.mapper.FieldInfoMapper;
import pl.luki2183.farmManager.fieldInfo.repo.FieldInfoRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class FieldInfoGetService {

    private final FieldInfoRepository repository;
    private final FieldInfoMapper mapper;

    public List<FieldInfoDto> getAllInfo() {
        return mapper.infoToDtoList(repository.findAll());
    }

    public FieldInfoDto getInfoById(String id) {
//        todo implement method
        return null;
    }
}
