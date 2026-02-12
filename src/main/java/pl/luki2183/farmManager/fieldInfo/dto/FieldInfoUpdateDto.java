package pl.luki2183.farmManager.fieldInfo.dto;

import lombok.Data;

@Data
public class FieldInfoUpdateDto {
    private String fieldId;
    private Double surfaceArea;
    private String grainType;
    private String plantDate;
    private String expectedHarvestDate;
    private String fieldColor;
}
