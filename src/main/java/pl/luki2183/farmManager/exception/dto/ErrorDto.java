package pl.luki2183.farmManager.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDto {
    /** The list of individual error details included in this response. */
    private List<FieldError> errorList;
    private int count;
}
