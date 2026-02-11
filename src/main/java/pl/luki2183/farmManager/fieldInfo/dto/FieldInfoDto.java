package pl.luki2183.farmManager.fieldInfo.dto;

import lombok.Data;

@Data
public class FieldInfoDto {
    private String fieldId;
    private Double surfaceArea;
    private String grainType;
    private String plantDate;
    private String expectedHarvestDate;
    private Double humidity;
    private Double windSpeed;
    private String fieldColor;
}
