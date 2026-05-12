package pl.luki2183.farmManager.exception.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data Transfer Object representing an error response payload.
 *
 * <p>Wraps a list of {@link FieldError} entries, allowing multiple
 * errors to be returned in a single response if needed.</p>
 */
@Data
@NoArgsConstructor
public class ErrorDto {
    /** The list of individual error details included in this response. */
    List<FieldError> errorList;
}
