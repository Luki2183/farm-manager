package pl.luki2183.farmManager.settings.dto;

import lombok.Data;
import pl.luki2183.farmManager.fieldInfo.model.Grain;
import pl.luki2183.farmManager.fields.dto.PointDto;

import java.util.Map;

/**
 * Data Transfer Object representing weather information for a field.
 *
 * <p>Used to transfer weather data between application layers,
 * corresponding to {@link pl.luki2183.farmManager.settings.model.SettingsEntity SettingsEntity}.</p>
 */
@Data
public class SettingsDto {
    /** Geographic center point used as the default map focus. */
    private PointDto center;
    /**
     * Mapping of each {@link pl.luki2183.farmManager.fieldInfo.model.Grain} type
     * to its display color, stored as a hex color string (e.g., {@code "#FF5733"}).
     */
    private Map<Grain, String> colorMap;
}
