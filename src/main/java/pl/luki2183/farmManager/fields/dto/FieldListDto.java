package pl.luki2183.farmManager.fields.dto;

import lombok.Data;

import java.util.List;

/**
 * Data Transfer Object representing or complete list of fields.
 *
 * <p>Wraps a list of {@link FieldDto} objects alongside a total count,
 * suitable for bulk retrieval responses.</p>
 */
@Data
public class FieldListDto {
    /** The list of field DTOs. */
    private List<FieldDto> dtoList;
    /** Total number of fields in the list. */
    private int count;
}
