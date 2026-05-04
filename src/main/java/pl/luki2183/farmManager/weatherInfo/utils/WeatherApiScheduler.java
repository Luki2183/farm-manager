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

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
        log.info("Scheduling weatherInfoUpdate");
        Map<String, PointEntity> fieldIdToCenterMap = fieldInfoGetService.getAllInfo().stream()
                .map(FieldInfoDto::getFieldId)
                .map(fieldFinder::find)
                .collect(Collectors.toMap(
                        FieldEntity::getFieldId,
                        entity -> coordinatesHelper.getCenter(entity.getCoordinates()))
                );

        fieldIdToCenterMap.forEach((fieldId, center) -> {
            WeatherInfoEntity weatherInfo = weatherGetService.getWeatherInfo(center);
            fieldInfoPutService.updateWeather(fieldId, weatherInfo);
        });
    }
}
