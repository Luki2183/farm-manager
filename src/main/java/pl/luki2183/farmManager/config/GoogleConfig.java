package pl.luki2183.farmManager.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
/**
 * Configuration properties bean for the Google API integration.
 *
 * <p>Binds properties prefixed with {@code google.api} from
 * {@code application.properties} or {@code application.yml}.
 * Example configuration:</p>
 *
 * <p>google.api.key=API_KEY</p>
 */
@Setter
@Getter
@Configuration
@EnableConfigurationProperties(GoogleConfig.class)
@ConfigurationProperties("google.api")
public class GoogleConfig {
    /** The Google API key used to authenticate requests to Google services. */
    private String key;
}
