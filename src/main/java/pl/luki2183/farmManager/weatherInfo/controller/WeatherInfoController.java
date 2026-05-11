package pl.luki2183.farmManager.weatherInfo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.luki2183.farmManager.fields.dto.PointDto;
import pl.luki2183.farmManager.fields.mapper.PointMapper;
import pl.luki2183.farmManager.fields.model.PointEntity;
import pl.luki2183.farmManager.weatherInfo.dto.WeatherInfoDto;
import pl.luki2183.farmManager.weatherInfo.service.WeatherGetService;

/**
 * REST controller exposing weather information endpoints.
 *
 * <p>Base path: {@code /api/weather}</p>
 *
 * <p>Business logic delegated to {@link WeatherGetService}</p>
 * and uses {@link PointMapper} to convert incoming DTOs to Entities.
 */
@Slf4j
@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherInfoController {

    private final WeatherGetService getService;
    private final PointMapper mapper;

    /**
     * Retrieves current weather info for given geographical point.
     * @param dto a {@link PointDto} class object
     * @return {@link ResponseEntity} containing {@link WeatherInfoDto}.
     */
    @GetMapping
    public ResponseEntity<WeatherInfoDto> getWeatherInfo(
            @RequestBody PointDto dto
    ) {
        log.info("Received request to get weather info for point: {}", dto);
        PointEntity point = mapper.fromDto(dto);
        WeatherInfoDto weatherInfo = getService.getWeatherInfoDto(point);
        log.info("Successfully retrieved weather info: {}", weatherInfo);
        return ResponseEntity.ok(weatherInfo);
    }
}
