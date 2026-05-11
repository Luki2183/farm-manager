package pl.luki2183.farmManager.utils;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

/**
 * Spring-managed component providing a shared {@link DateTimeFormatter}
 * for the {@code yyyy-MM-dd} date pattern (ISO 8601 local date).
 *
 * <p>Inject this bean wherever consistent date formatting or parsing is needed,
 * rather than creating formatter instances inline.</p>
 */
@Component
@Getter
public class DateFormat {
    /** Formatter for dates in {@code yyyy-MM-dd} format (e.g., {@code "2026-05-10"}). */
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
}
