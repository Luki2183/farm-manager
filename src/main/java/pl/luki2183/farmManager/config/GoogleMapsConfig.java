package pl.luki2183.farmManager.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@EnableConfigurationProperties(GoogleMapsConfig.class)
@ConfigurationProperties("google.maps.api")
public class GoogleMapsConfig {
    private String key;
}
