package pl.luki2183.farmManager.weatherInfo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.luki2183.farmManager.config.GoogleConfig;
import pl.luki2183.farmManager.exception.WeatherInfoException;
import pl.luki2183.farmManager.fields.model.PointEntity;
import pl.luki2183.farmManager.weatherInfo.dto.WeatherDto;
import pl.luki2183.farmManager.weatherInfo.mapper.WeatherInfoMapper;
import pl.luki2183.farmManager.weatherInfo.model.WeatherInfoEntity;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherGetService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final GoogleConfig googleConfig;
    private final WeatherInfoMapper mapper;
    private final URI baseUrl = URI.create("https://weather.googleapis.com/v1/currentConditions:lookup");

    public WeatherInfoEntity getWeatherInfo(PointEntity point) {
        log.info("Fetching weather for coordinates: {} {}", point.getLat(), point.getLng());

        String url = UriComponentsBuilder.fromUri(baseUrl)
                .queryParam("key", googleConfig.getKey())
                .queryParam("location.latitude", point.getLat())
                .queryParam("location.longitude", point.getLng())
                .toUriString();

        return fetchSingle(url, point);
    }

    private WeatherInfoEntity fetchSingle(String url, PointEntity point) {
        try {
            WeatherDto dto = restTemplate.getForObject(url, WeatherDto.class);
            if (dto == null) {
                throw new WeatherInfoException("Empty response for: " + point.toString());
            }
            return mapper.fromDto(dto);
        } catch (RestClientException e) {
            log.error("HTTP error fetching weather for '{}': {}", point, e.getMessage());
            throw new WeatherInfoException("Failed to fetch weather for: " + point.toString(), e);
        }
    }
}
