package pl.luki2183.farmManager.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.luki2183.farmManager.repo.FieldRepository;

@Service
@RequiredArgsConstructor
public class FieldDeleteService {

    private final FieldRepository repository;

    public void deleteFieldById(String id) {
        repository.deleteById(id);
    }
}
