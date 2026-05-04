package pl.luki2183.farmManager.weatherInfo.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.luki2183.farmManager.fields.model.PointEntity;

import java.util.List;

@Slf4j
@Component
public class CoordinatesHelper {
    public PointEntity getCenter(List<PointEntity> pointEntityList) {
        log.info("Entering getCenter with data: {}", pointEntityList);
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
        log.info("Created center point with result: {}", pointEntity);
        return pointEntity;
    }
}
