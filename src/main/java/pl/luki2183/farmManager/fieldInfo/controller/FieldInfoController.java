package pl.luki2183.farmManager.fieldInfo.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.luki2183.farmManager.fieldInfo.dto.FieldInfoDto;
import pl.luki2183.farmManager.fieldInfo.model.FieldInfoEntity;
import pl.luki2183.farmManager.fieldInfo.service.FieldInfoDeleteService;
import pl.luki2183.farmManager.fieldInfo.service.FieldInfoGetService;
import pl.luki2183.farmManager.fieldInfo.service.FieldInfoPostService;
import pl.luki2183.farmManager.fieldInfo.service.FieldInfoPutService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/fieldInfo")
public class FieldInfoController {

    private final FieldInfoGetService getService;
    private final FieldInfoPostService postService;
    private final FieldInfoPutService putService;
    private final FieldInfoDeleteService deleteService;

    @GetMapping
    public List<FieldInfoDto> getAllInfo() {
        return getService.getAllInfo();
    }

    @GetMapping("/{fieldId}")
    public FieldInfoDto getInfoByFieldId(@PathVariable String fieldId) {
        return getService.getInfoByFieldId(fieldId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FieldInfoEntity addFieldInfo(@RequestBody FieldInfoDto dto) {
        return postService.addInfo(dto);
    }

    @PutMapping("/{fieldId}")
    @ResponseStatus(HttpStatus.OK)
    public FieldInfoEntity updateFieldInfo(@RequestBody FieldInfoDto dto) {
        return putService.updateInfo(dto);
    }

    @DeleteMapping("/{fieldId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFieldInfoById(@PathVariable String fieldId) {
        deleteService.deleteById(fieldId);
    }
}
