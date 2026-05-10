package pl.luki2183.farmManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import pl.luki2183.farmManager.config.DataInitializer;

@SpringBootApplication
@EnableScheduling
public class FarmManagerApplication {

	static void main(String[] args) {
		SpringApplication.run(FarmManagerApplication.class, args);
	}

}
