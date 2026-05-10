package pl.luki2183.farmManager.settings.dto;

import lombok.Data;
import pl.luki2183.farmManager.fieldInfo.model.Grain;
import pl.luki2183.farmManager.fields.dto.PointDto;

import java.util.Map;

@Data
public class SettingsDto {
    private PointDto center;
    private Map<Grain, String> colorMap;
}
