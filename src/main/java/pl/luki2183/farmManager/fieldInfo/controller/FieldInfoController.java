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

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/fieldInfo")
public class FieldInfoController {

    private final FieldInfoGetService getService;
    private final FieldInfoPostService postService;
    private final FieldInfoPutService putService;
    private final FieldInfoDeleteService deleteService;

    @GetMapping
    public ResponseEntity<FieldInfoListDto> getAllInfo() {
        log.info("Received request to get all field infos");
        FieldInfoListDto result = getService.getAllInfo();
        log.info("Successfully retrieved all field infos: {}", result);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{fieldId}")
    public ResponseEntity<FieldInfoDto> getInfoByFieldId(
            @PathVariable String fieldId
    ) {
        log.info("Received request to get field info with id: {}", fieldId);
        FieldInfoDto result = getService.getInfoByFieldId(fieldId);
        log.info("Successfully retrieved field info: {}", result);
        return ResponseEntity.ok(result);
    }

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
