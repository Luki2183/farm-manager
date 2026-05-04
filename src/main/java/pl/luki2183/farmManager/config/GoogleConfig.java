package pl.luki2183.farmManager.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@EnableConfigurationProperties(GoogleConfig.class)
@ConfigurationProperties("google.api")
public class GoogleConfig {
    private String key;
}
