package pl.luki2183.farmManager.weatherInfo.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.luki2183.farmManager.fields.model.PointEntity;

import java.util.List;

/**
 * Helper class to calculate center point of polygon
 */
@Slf4j
@Component
public class CoordinatesHelper {
    /**
     * <p>Create a center point of any provided coordinates list</p>
     * <b>Note:</b> center point coordinates are created by following this order
     * <ol>
     *     <li>Selects min latitude and longitude from provided coordinates</li>
     *     <li>Selects max latitude and longitude from provided coordinates</li>
     *     <li>Calculates center point latitude by adding max and min values and dividing by 2</li>
     *     <li>Calculates center point longitude by adding max and min values and dividing by 2</li>
     * </ol>
     * @param pointEntityList List of point coordinates provided as {@code List<PointEntity>}
     * @return {@link pl.luki2183.farmManager.fields.model.PointEntity}
     */
    public PointEntity getCenter(List<PointEntity> pointEntityList) {
        log.debug("Entering getCenter with points: {}", pointEntityList);
        double minLng = 180;
        double maxLng = -180;
        double minLat = 90;
        double maxLat = -90;

        for (PointEntity pointEntity : pointEntityList) {
            minLng = Math.min(minLng, pointEntity.getLng());
            maxLng = Math.max(maxLng, pointEntity.getLng());
            minLat = Math.min(minLat, pointEntity.getLat());
            maxLat = Math.max(maxLat, pointEntity.getLat());
        }
        PointEntity pointEntity = new PointEntity();
        pointEntity.setLng(((minLng + maxLng) / 2));
        pointEntity.setLat(((minLat + maxLat) / 2));
        log.debug("Calculated center point: lat={}, lng={}", pointEntity.getLat(), pointEntity.getLng());
        return pointEntity;
    }
}
