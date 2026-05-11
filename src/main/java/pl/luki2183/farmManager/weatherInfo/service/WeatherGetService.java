package pl.luki2183.farmManager.weatherInfo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.luki2183.farmManager.config.GoogleConfig;
import pl.luki2183.farmManager.exception.model.WeatherInfoException;
import pl.luki2183.farmManager.fields.model.PointEntity;
import pl.luki2183.farmManager.weatherInfo.dto.WeatherDto;
import pl.luki2183.farmManager.weatherInfo.dto.WeatherInfoDto;
import pl.luki2183.farmManager.weatherInfo.mapper.WeatherInfoMapper;
import pl.luki2183.farmManager.weatherInfo.model.WeatherInfoEntity;

import java.net.URI;

/**
 * Service class for fetching external weather data.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherGetService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final GoogleConfig googleConfig;
    private final WeatherInfoMapper mapper;
    private final URI baseUrl = URI.create("https://weather.googleapis.com/v1/currentConditions:lookup");

    /**
     * <p>Prepares Uri with queryParams as such:</p>
     * <ul>
     *     <li>"key"</li>
     *     <li>"location.latitude"</li>
     *     <li>"location.longitude"</li>
     * </ul>
     * <p>then uses private {@code fetchSingle} method to receive DTO from Google Weather Services.</p>
     * @param point point with lat and lng
     * @return {@link pl.luki2183.farmManager.weatherInfo.model.WeatherInfoEntity}
     */
    public WeatherInfoEntity getWeatherInfoEntity(PointEntity point) {
        log.info("Fetching weather info for coordinates: lat={} lng={}", point.getLat(), point.getLng());

        String url = UriComponentsBuilder.fromUri(baseUrl)
                .queryParam("key", googleConfig.getKey())
                .queryParam("location.latitude", point.getLat())
                .queryParam("location.longitude", point.getLng())
                .toUriString();
        log.debug("Build request URL: {}", url);
        return fetchSingle(url, point);
    }

    /**
     * Reuses {@code getWeatherInfoEntity} to fetch and map weather info for controller response.
     * <p>It's only use is inside the controller.</p>
     * @param point point with lat and lng
     * @return {@link WeatherInfoDto}
     */
    public WeatherInfoDto getWeatherInfoDto(PointEntity point) {
        return mapper.fromEntityToDto(getWeatherInfoEntity(point));
    }

    private WeatherInfoEntity fetchSingle(String url, PointEntity point) {
        log.debug("Entering fetchSingle with params: url={}, point={}", url, point);
        try {
            WeatherDto dto = restTemplate.getForObject(url, WeatherDto.class);
            if (dto == null) {
                log.warn("Received null response from weather API for point: {}", point);
                throw new WeatherInfoException("Empty response for: " + point.toString());
            }
            WeatherInfoEntity result = mapper.fromResponseToEntity(dto);
            log.debug("Successfully fetched and mapped weather data for point: {}", point);
            return result;
        } catch (RestClientException e) {
            log.error("HTTP error fetching weather for '{}': {}", point, e.getMessage());
            throw new WeatherInfoException("Failed to fetch weather for: " + point.toString(), e);
        }
    }
}
