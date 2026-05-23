package pl.luki2183.farmManager.fields.service.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.luki2183.farmManager.fields.mapper.FieldMapper;
import pl.luki2183.farmManager.fields.repo.FieldRepository;
import pl.luki2183.farmManager.fields.service.FieldGetService;
import pl.luki2183.farmManager.fields.utils.FieldFinder;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FieldGetServiceUnitTest {

    @Mock
    private FieldRepository repository;

    @Mock
    private FieldMapper mapper;

    @Mock
    private FieldFinder finder;

    @InjectMocks
    private FieldGetService sut;

    @Test
    void getAllFields() {
    }

    @Test
    void getFieldWithId() {
    }

    @Test
    void checkIfExistsByFieldId() {
    }
}