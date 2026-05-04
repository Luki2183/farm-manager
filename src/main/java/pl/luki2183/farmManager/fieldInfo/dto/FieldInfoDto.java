package pl.luki2183.farmManager.fieldInfo.dto;

import lombok.Data;

@Data
public class FieldInfoDto {
    private String fieldId;
    private double surfaceArea;
    private String grainType;
    private String plantDate;
    private String expectedHarvestDate;
    private double humidity;
    private double windSpeed;
    private String fieldColor;
}
