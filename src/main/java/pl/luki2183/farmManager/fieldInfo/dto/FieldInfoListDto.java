package pl.luki2183.farmManager.fieldInfo.dto;

import lombok.Data;

import java.util.List;

/**
 * Data Transfer Object representing a list of field info records.
 *
 * <p>Wraps a list of {@link FieldInfoDto} objects alongside a total count,
 * suitable for bulk retrieval and filtered list responses.</p>
 */
@Data
public class FieldInfoListDto {
    /** The list of field info DTOs. */
    private List<FieldInfoDto> dtoList;
    /** Total number of field info records in the list. */
    private int count;
}
