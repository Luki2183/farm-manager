package pl.luki2183.farmManager.fieldInfo.controller;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.luki2183.farmManager.fieldInfo.dto.FieldInfoCreateDto;
import pl.luki2183.farmManager.fieldInfo.dto.FieldInfoDto;
import pl.luki2183.farmManager.fieldInfo.dto.FieldInfoListDto;
import pl.luki2183.farmManager.fieldInfo.dto.FieldInfoUpdateDto;
import pl.luki2183.farmManager.fieldInfo.service.FieldInfoDeleteService;
import pl.luki2183.farmManager.fieldInfo.service.FieldInfoGetService;
import pl.luki2183.farmManager.fieldInfo.service.FieldInfoPostService;
import pl.luki2183.farmManager.fieldInfo.service.FieldInfoPutService;

import java.net.URI;

/**
 * REST controller exposing CRUD endpoints for field info records.
 *
 * <p>Base path: {@code /api/fieldInfo}</p>
 *
 * <p>Delegates read operations to {@link FieldInfoGetService}, creation to
 * {@link FieldInfoPostService}, updates to {@link FieldInfoPutService},
 * and deletion to {@link FieldInfoDeleteService}.</p>
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/fieldInfo")
public class FieldInfoController {

    private final FieldInfoGetService getService;
    private final FieldInfoPostService postService;
    private final FieldInfoPutService putService;
    private final FieldInfoDeleteService deleteService;

    /**
     * Retrieves all field info records.
     *
     * @return {@link ResponseEntity} containing a {@link FieldInfoListDto} of all field infos
     */
    @GetMapping
    public ResponseEntity<FieldInfoListDto> getAllInfo() {
        log.info("Received request to get all field infos");
        FieldInfoListDto result = getService.getAllInfo();
        log.info("Successfully retrieved all field infos: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a single field info record by its field ID.
     *
     * @param fieldId the business identifier of the field
     * @return {@link ResponseEntity} containing the matching {@link FieldInfoDto}
     * @throws pl.luki2183.farmManager.exception.model.FieldInfoEntityNotFoundException
     *         if no field info with the given ID exists, thrown by
     *         {@link pl.luki2183.farmManager.fieldInfo.utils.FieldInfoFinder#find(String) FieldInfoFinder.find(String)}
     */
    @GetMapping("/{fieldId}")
    public ResponseEntity<FieldInfoDto> getInfoByFieldId(
            @PathVariable String fieldId
    ) {
        log.info("Received request to get field info with id: {}", fieldId);
        FieldInfoDto result = getService.getInfoByFieldId(fieldId);
        log.info("Successfully retrieved field info: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Creates a new field info record from the provided data.
     *
     * @param dto the {@link FieldInfoCreateDto} describing the new field info
     * @return {@link ResponseEntity} with status {@code 201 Created} and the
     *         persisted {@link FieldInfoDto} in the body; the {@code Location} header
     *         points to {@code /api/fieldInfo/{fieldId}}
     * @throws pl.luki2183.farmManager.exception.model.PrimaryKeyViolationException
     *         if a field info with the same ID already exists, thrown by
     *         {@link pl.luki2183.farmManager.fieldInfo.utils.FieldInfoFinder#exists(String) FieldInfoFinder.exists(String)}
     */
    @PostMapping
    public ResponseEntity<FieldInfoDto> addFieldInfo(
            @RequestBody FieldInfoCreateDto dto
    ) {
        log.info("Received request to create FieldInfoEntity: {}", dto);
        FieldInfoDto result = postService.addInfo(dto);
        log.info("Successfully created FieldInfoEntity: {}", result);
        return ResponseEntity
                .created(URI.create("/api/fieldInfo/".concat(result.getFieldId())))
                .body(result);
    }

    /**
     * Updates an existing field info record by its field ID.
     *
     * @param dto     the {@link FieldInfoUpdateDto} containing the updated data
     * @param fieldId the business identifier of the field info to update
     * @return {@link ResponseEntity} containing the updated {@link FieldInfoDto}
     * @throws pl.luki2183.farmManager.exception.model.FieldInfoEntityNotFoundException
     *         if no field info with the given ID exists, thrown by
     *         {@link pl.luki2183.farmManager.fieldInfo.utils.FieldInfoFinder#find(String) FieldInfoFinder.find(String)}
     */
    @PutMapping("/{fieldId}")
    public ResponseEntity<FieldInfoDto> updateFieldInfo(
            @RequestBody FieldInfoUpdateDto dto,
            @PathVariable String fieldId
    ) {
        log.info("Received request to update field info with id: {}, and data: {}", fieldId, dto);
        FieldInfoDto result = putService.updateInfo(dto, fieldId);
        log.info("Successfully updated FieldInfoEntity: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Deletes a field info record by its field ID.
     *
     * @param fieldId the business identifier of the field info to delete
     * @return {@link ResponseEntity} with status {@code 204 No Content}
     * @throws pl.luki2183.farmManager.exception.model.FieldInfoEntityNotFoundException
     *         if no field info with the given ID exists, thrown by
     *         {@link pl.luki2183.farmManager.fieldInfo.utils.FieldInfoFinder#find(String) FieldInfoFinder.find(String)}
     */
    @DeleteMapping("/{fieldId}")
    public ResponseEntity<Void> deleteFieldInfoById(
            @PathVariable String fieldId
    ) {
        log.info("Received request to delete field with id: {}", fieldId);
        deleteService.deleteById(fieldId);
        log.info("Successfully deleted FieldInfoEntity with id: {}", fieldId);
        return ResponseEntity
                .noContent()
                .build();
    }
}
