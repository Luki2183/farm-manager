package pl.luki2183.farmManager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.luki2183.farmManager.entity.FieldEntity;
import pl.luki2183.farmManager.repo.FieldRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FieldGetService {

    private final FieldRepository repository;

    public List<FieldEntity> getAllFields(){
        return repository.findAll();
    }
}
