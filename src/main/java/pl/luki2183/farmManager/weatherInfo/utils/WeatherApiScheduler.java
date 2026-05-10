package pl.luki2183.farmManager.weatherInfo.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.luki2183.farmManager.fieldInfo.dto.FieldInfoDto;
import pl.luki2183.farmManager.fieldInfo.service.FieldInfoGetService;
import pl.luki2183.farmManager.fieldInfo.service.FieldInfoPutService;
import pl.luki2183.farmManager.fields.model.FieldEntity;
import pl.luki2183.farmManager.fields.model.PointEntity;
import pl.luki2183.farmManager.fields.utils.FieldFinder;
import pl.luki2183.farmManager.weatherInfo.model.WeatherInfoEntity;
import pl.luki2183.farmManager.weatherInfo.service.WeatherGetService;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Simple scheduler to periodically update weather infos
 * of all available fields.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WeatherApiScheduler {

    private final WeatherGetService weatherGetService;
    private final FieldInfoGetService fieldInfoGetService;
    private final FieldInfoPutService fieldInfoPutService;
    private final FieldFinder fieldFinder;
    private final CoordinatesHelper coordinatesHelper;

    @Scheduled(fixedRate = 2, timeUnit = TimeUnit.HOURS)
    public void updateAllWeatherInfo() {
        log.info("Starting scheduled weather update for all fields");
        Map<String, PointEntity> fieldIdToCenterMap = fieldInfoGetService.getAllInfo().stream()
                .map(FieldInfoDto::getFieldId)
                .map(fieldFinder::find)
                .collect(Collectors.toMap(
                        FieldEntity::getFieldId,
                        entity -> coordinatesHelper.getCenter(entity.getCoordinates()))
                );
        log.info("Updating weather for {} field(s)", fieldIdToCenterMap.size());

        fieldIdToCenterMap.forEach((fieldId, center) -> {
            log.debug("Fetching weather for fieldId={} at center: lat={}, lng={}", fieldId, center.getLat(), center.getLng());
            WeatherInfoEntity weatherInfo = weatherGetService.getWeatherInfo(center);
            fieldInfoPutService.updateWeather(fieldId, weatherInfo);
            log.debug("Successfully updated weather for fieldId={}", fieldId);
        });

        log.info("Scheduled weather update completed for {} field(s)", fieldIdToCenterMap.size());
    }
}
