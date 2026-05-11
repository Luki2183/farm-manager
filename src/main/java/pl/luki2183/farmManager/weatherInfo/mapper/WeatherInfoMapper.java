package pl.luki2183.farmManager.weatherInfo.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.luki2183.farmManager.utils.Mapper;
import pl.luki2183.farmManager.weatherInfo.dto.WeatherDto;
import pl.luki2183.farmManager.weatherInfo.dto.WeatherInfoDto;
import pl.luki2183.farmManager.weatherInfo.model.WeatherInfoEntity;

/**
 * Mapper component providing bidirectional conversion between
 * {@link WeatherInfoEntity} and {@link WeatherInfoDto}.
 */
@Slf4j
@Component
public class WeatherInfoMapper implements Mapper<WeatherInfoEntity, WeatherInfoDto> {
    /**
     * Creates a {@link WeatherInfoEntity} object from {@link WeatherDto}.
     * @param dto object of {@link WeatherDto} class
     * @return {@link pl.luki2183.farmManager.weatherInfo.model.WeatherInfoEntity}
     */
    public WeatherInfoEntity fromResponseToEntity(WeatherDto dto) {
        log.debug("Mapping WeatherDto to WeatherInfoEntity: {}", dto);
        WeatherInfoEntity entity = new WeatherInfoEntity();
        entity.setHumidity(dto.getRelativeHumidity());
        entity.setWindSpeed(dto.getWind().getSpeed().getValue());
        log.debug("Mapped WeatherInfoEntity result: {}", entity);
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
        log.debug("Mapping WeatherInfoDto to WeatherInfoEntity: {}", dto);
        WeatherInfoEntity entity = new WeatherInfoEntity();
        entity.setWindSpeed(dto.getWindSpeed());
        entity.setHumidity(dto.getHumidity());
        log.debug("Mapped WeatherInfoEntity result: {}", entity);
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
        log.debug("Mapping WeatherInfoEntity to WeatherInfoDto: {}", entity);
        WeatherInfoDto dto = new WeatherInfoDto();
        dto.setHumidity(entity.getHumidity());
        dto.setWindSpeed(entity.getWindSpeed());
        log.debug("Mapped WeatherInfoDto result: {}", dto);
        return dto;
    }
}
