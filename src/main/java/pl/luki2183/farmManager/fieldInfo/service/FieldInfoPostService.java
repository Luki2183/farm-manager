package pl.luki2183.farmManager.fieldInfo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.luki2183.farmManager.fieldInfo.dto.FieldInfoDto;
import pl.luki2183.farmManager.fieldInfo.mapper.FieldInfoMapper;
import pl.luki2183.farmManager.fieldInfo.repo.FieldInfoRepository;

@Service
@AllArgsConstructor
public class FieldInfoPostService {

    private final FieldInfoRepository repository;
    private final FieldInfoMapper mapper;

    public void addInfo(FieldInfoDto dto) {
//        todo implement method
    }
}
