package pl.luki2183.farmManager.weatherInfo.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.luki2183.farmManager.utils.Mapper;
import pl.luki2183.farmManager.weatherInfo.dto.WeatherDto;
import pl.luki2183.farmManager.weatherInfo.dto.WeatherInfoDto;
import pl.luki2183.farmManager.weatherInfo.model.WeatherInfoEntity;

/**
 * Mapping class for WeatherInfo.
 */
@Slf4j
@Component
public class WeatherInfoMapper implements Mapper<WeatherInfoEntity, WeatherInfoDto> {
    /**
     * Creates a {@code WeatherInfoEntity} object from provided data transfer object.
     * @param dto object of {@link WeatherDto} class
     * @return {@link pl.luki2183.farmManager.weatherInfo.model.WeatherInfoEntity}
     */
    public WeatherInfoEntity fromResponseToEntity(WeatherDto dto) {
        log.debug("Entering fromResponseToEntity with params: {}", dto);
        WeatherInfoEntity entity = new WeatherInfoEntity();
        entity.setHumidity(dto.getRelativeHumidity());
        entity.setWindSpeed(dto.getWind().getSpeed().getValue());
        log.debug("Mapped WeatherDto to WeatherInfoEntity: {}", entity);
        return entity;
    }

    /**
     * Converts a DTO into its corresponding entity representation.
     *
     * @param dto object of {@link WeatherInfoDto}
     * @return {@link WeatherInfoEntity}
     */
    @Override
    public WeatherInfoEntity fromDtoToEntity(WeatherInfoDto dto) {
        log.debug("Entering fromDtoToEntity with params: {}", dto);
        WeatherInfoEntity entity = new WeatherInfoEntity();
        entity.setWindSpeed(dto.getWindSpeed());
        entity.setHumidity(dto.getHumidity());
        log.debug("Mapped WeatherInfoDto to WeatherInfoEntity: {}", entity);
        return entity;
    }

    /**
     * Converts an entity into its corresponding DTO representation.
     *
     * @param entity object of {@link WeatherInfoEntity}
     * @return {@link WeatherInfoDto}
     */
    @Override
    public WeatherInfoDto fromEntityToDto(WeatherInfoEntity entity) {
        log.debug("Entering fromEntityToDto with params: {}", entity);
        WeatherInfoDto dto = new WeatherInfoDto();
        dto.setHumidity(entity.getHumidity());
        dto.setWindSpeed(entity.getWindSpeed());
        log.debug("Mapped WeatherInfoEntity to WeatherInfoDto: {}", dto);
        return dto;
    }
}
