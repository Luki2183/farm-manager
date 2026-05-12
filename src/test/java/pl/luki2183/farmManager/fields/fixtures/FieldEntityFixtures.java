package pl.luki2183.farmManager.fields.fixtures;

import pl.luki2183.farmManager.fieldInfo.model.FieldInfoEntity;
import pl.luki2183.farmManager.fieldInfo.model.Grain;
import pl.luki2183.farmManager.fields.model.FieldEntity;
import pl.luki2183.farmManager.fields.model.PointEntity;

import java.util.List;

public class FieldEntityFixtures {
    public static FieldEntity.FieldEntityBuilder valid() {
        return FieldEntity.builder()
                .fieldId("field-1")
                .coordinates(List.of(PointEntity.builder().lng(21.0).lat(51.0).build()))
                .fieldInfo(
                        FieldInfoEntity.builder()
                                .field(FieldEntity.builder().build())
                                .fieldId("field-1")
                                .grainType(Grain.CARROT)
                                .build()
                );
    }
}
