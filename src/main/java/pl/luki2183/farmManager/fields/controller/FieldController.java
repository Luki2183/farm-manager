package pl.luki2183.farmManager.fields.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.luki2183.farmManager.fields.dto.FieldDto;
import pl.luki2183.farmManager.fields.dto.FieldListDto;
import pl.luki2183.farmManager.fields.dto.GeoJSONDto;
import pl.luki2183.farmManager.fields.service.FieldDeleteService;
import pl.luki2183.farmManager.fields.service.FieldGetService;
import pl.luki2183.farmManager.fields.service.FieldPostService;
import pl.luki2183.farmManager.fields.service.FieldPutService;

import java.net.URI;

/**
 * REST controller exposing CRUD endpoints for farm fields.
 *
 * <p>Base path: {@code /api/fields}</p>
 *
 * <p>Delegates read operations to {@link FieldGetService}, creation to
 * {@link FieldPostService}, updates to {@link FieldPutService},
 * and deletion to {@link FieldDeleteService}.</p>
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api/fields")
@RequiredArgsConstructor
public class FieldController {

    private final FieldGetService getService;
    private final FieldPostService postService;
    private final FieldDeleteService deleteService;
    private final FieldPutService putService;

    /**
     * Retrieves all fields.
     *
     * @return {@link ResponseEntity} containing a {@link FieldListDto} of all fields
     */
    @GetMapping
    public ResponseEntity<FieldListDto> getAllFields(){
        log.info("Received request to get all fields");
        FieldListDto result = getService.getAllFields();
        log.info("Successfully retrieved all fields: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a single field by its field ID.
     *
     * @param fieldId the business identifier of the field
     * @return {@link ResponseEntity} containing the matching {@link FieldDto}
     * @throws pl.luki2183.farmManager.exception.model.FieldEntityNotFoundException
     *         if no field with the given ID exists, thrown by
     *         {@link pl.luki2183.farmManager.fields.utils.FieldFinder#find(String) FieldFinder.find(String)}
     */
    @GetMapping("/{fieldId}")
    public ResponseEntity<FieldDto> getFieldWithId(
            @NotBlank @PathVariable String fieldId
    ) {
        log.info("Received request to get field with id: {}", fieldId);
        FieldDto result = getService.getFieldWithId(fieldId);
        log.info("Successfully retrieved field: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Checks whether a field with the given ID exists.
     *
     * @param fieldId the business identifier of the field to check
     * @return {@link ResponseEntity} containing {@code true} if the field exists,
     *         {@code false} otherwise
     */
    @GetMapping("/exists/{fieldId}")
    public ResponseEntity<Boolean> fieldExists(
            @NotBlank @PathVariable String fieldId
    ) {
        log.info("Received request to check if field exists with id: {}", fieldId);
        Boolean result = getService.checkIfExistsByFieldId(fieldId);
        log.info("Successfully checked if field exists: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Creates a new field from a GeoJSON representation.
     *
     * @param dto the {@link GeoJSONDto} describing the field geometry and properties
     * @return {@link ResponseEntity} with status {@code 201 Created} and the
     *         persisted {@link FieldDto} in the body; the {@code Location} header
     *         points to {@code /api/fields/{id}}
     * @throws pl.luki2183.farmManager.exception.model.PrimaryKeyViolationException
     *         if a field with the same ID already exists, thrown by
     *         {@link pl.luki2183.farmManager.fields.utils.FieldFinder#exists(String) FieldFinder.exists(String)}
     */
    @PostMapping
    public ResponseEntity<FieldDto> addField(
            @Valid @RequestBody GeoJSONDto dto
    ){
        log.info("Received request to create FieldEntity: {}", dto);
        FieldDto result = postService.saveField(dto);
        log.info("Successfully created FieldEntity: {}", result);
        return ResponseEntity
                .created(URI.create("/api/fields/"+ result.getId()))
                .body(result);
    }

    /**
     * Updates the geometry of an existing field.
     *
     * @param dto     the {@link GeoJSONDto} containing the updated field data
     * @param fieldId the business identifier of the field to update
     * @return {@link ResponseEntity} containing the updated {@link FieldDto}
     * @throws pl.luki2183.farmManager.exception.model.FieldEntityNotFoundException
     *         if no field with the given ID exists, thrown by
     *         {@link pl.luki2183.farmManager.fields.utils.FieldFinder#find(String) FieldFinder.find(String)}
     */
    @PutMapping("/{fieldId}")
    public ResponseEntity<FieldDto> updateField(
            @NotBlank @PathVariable String fieldId,
            @Valid @RequestBody GeoJSONDto dto
    ) {
        log.info("Received request to update field with id: {}, and data: {}", fieldId, dto);
        FieldDto result = putService.updateField(fieldId, dto);
        log.info("Successfully updated FieldEntity: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Deletes a field by its field ID.
     *
     * @param fieldId the business identifier of the field to delete
     * @return {@link ResponseEntity} with status {@code 204 No Content}
     * @throws pl.luki2183.farmManager.exception.model.FieldEntityNotFoundException
     *         if no field with the given ID exists, thrown by
     *         {@link pl.luki2183.farmManager.fields.utils.FieldFinder#find(String) FieldFinder.find(String)}
     */
    @DeleteMapping("/{fieldId}")
    public ResponseEntity<Void> deleteField(
            @NotBlank @PathVariable String fieldId
    ){
        log.info("Received request to delete field with id: {}", fieldId);
        deleteService.deleteFieldById(fieldId);
        log.info("Successfully deleted FieldEntity with id: {}", fieldId);
        return ResponseEntity
                .noContent()
                .build();
    }
}
