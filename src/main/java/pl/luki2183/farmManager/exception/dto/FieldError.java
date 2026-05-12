package pl.luki2183.farmManager.exception.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * Represents a single error entry within an {@link ErrorDto} response.
 *
 * <p>Carries the HTTP status code, a human-readable message, and the
 * request path that triggered the error.</p>
 */
@Data
@Builder
public class FieldError {
    /** Human-readable description of the error. */
    private String message;
    /** The request URI path that triggered this error. */
    private String path;
    /** The HTTP status code associated with this error. */
    private HttpStatus code;
}
