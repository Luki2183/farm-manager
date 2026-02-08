package pl.luki2183.farmManager.fieldInfo.dto;

import lombok.Data;
import pl.luki2183.farmManager.fieldInfo.model.Grain;

import java.awt.*;
import java.util.Date;

@Data
public class FieldInfoDto {
    private String fieldId;
    private Double surfaceArea;
    private Grain grainType;
    private Date plantDate;
    private Date expectedHarvestDate;
    private Double humidity;
    private Double windSpeed;
    private Color fieldColor;
}
