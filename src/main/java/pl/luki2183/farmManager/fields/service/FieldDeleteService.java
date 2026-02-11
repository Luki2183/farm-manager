package pl.luki2183.farmManager.fields.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.luki2183.farmManager.fields.repo.FieldRepository;

@Service
@RequiredArgsConstructor
public class FieldDeleteService {

    private final FieldRepository repository;

    @Transactional
    public void deleteFieldById(String id) {
        repository.deleteByFieldId(id);
    }
}
