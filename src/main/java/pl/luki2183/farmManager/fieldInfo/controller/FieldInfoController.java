package pl.luki2183.farmManager.fieldInfo.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.luki2183.farmManager.fieldInfo.dto.FieldInfoDto;
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

    @GetMapping("/{id}")
    public FieldInfoDto getInfoById(@PathVariable String id) {
        return getService.getInfoById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addFieldInfo(@RequestBody FieldInfoDto dto) {
        postService.addInfo(dto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateFieldInfo(@RequestBody FieldInfoDto dto) {
        putService.updateInfo(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFieldInfoById(@PathVariable String id) {
        deleteService.deleteById(id);
    }
}
