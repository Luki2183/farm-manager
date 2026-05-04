package pl.luki2183.farmManager.weatherInfo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.luki2183.farmManager.fields.dto.PointDto;
import pl.luki2183.farmManager.fields.mapper.PointMapper;
import pl.luki2183.farmManager.weatherInfo.model.WeatherInfoEntity;
import pl.luki2183.farmManager.weatherInfo.service.WeatherGetService;

@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherInfoController {

    private final WeatherGetService getService;
    private final PointMapper mapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public WeatherInfoEntity getWeatherInfo(
            @RequestBody PointDto dto
    ) {
        return getService.getWeatherInfo(mapper.fromDto(dto));
    }
}
