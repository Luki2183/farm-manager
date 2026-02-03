package pl.luki2183.farmManager.fields.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.luki2183.farmManager.fields.entity.FieldEntity;
import pl.luki2183.farmManager.fields.repo.FieldRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FieldGetService {

    private final FieldRepository repository;

    public List<FieldEntity> getAllFields(){
        return repository.findAll();
    }
}
