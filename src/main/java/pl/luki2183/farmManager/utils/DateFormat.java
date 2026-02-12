package pl.luki2183.farmManager.utils;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
@Getter
public class DateFormat {
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
}
