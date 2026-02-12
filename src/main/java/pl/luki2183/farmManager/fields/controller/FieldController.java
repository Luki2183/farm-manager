package pl.luki2183.farmManager.fields.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.luki2183.farmManager.fields.dto.FieldDto;
import pl.luki2183.farmManager.fields.dto.GeoJSONDto;
import pl.luki2183.farmManager.fields.model.FieldEntity;
import pl.luki2183.farmManager.fields.service.FieldGetService;
import pl.luki2183.farmManager.fields.service.FieldPostService;
import pl.luki2183.farmManager.fields.service.FieldDeleteService;
import pl.luki2183.farmManager.fields.service.FieldPutService;

import java.util.List;

@RestController
@RequestMapping("/api/fields")
@RequiredArgsConstructor
public class FieldController {

    private final FieldGetService getService;
    private final FieldPostService postService;
    private final FieldDeleteService deleteService;
    private final FieldPutService putService;

    @GetMapping
    public List<FieldDto> getAllFields(){
        return getService.getAllFields();
    }

    @GetMapping("/{fieldId}")
    public FieldDto getFieldWithId(@PathVariable String fieldId) { return getService.getFieldWithId(fieldId); }

    @GetMapping("/exists/{fieldId}")
    public Boolean fieldExists(@PathVariable String fieldId) { return getService.checkIfExistsByFieldId(fieldId); }

    @PostMapping
    public FieldEntity addField(@RequestBody GeoJSONDto dto){
        return postService.saveField(dto);
    }

    @PutMapping("/{fieldId}")
    public FieldEntity updateField(@RequestBody GeoJSONDto dto, @PathVariable String fieldId) { return putService.updateField(fieldId, dto); }

    @DeleteMapping("/{fieldId}")
    public void deleteField(@PathVariable String fieldId){
        deleteService.deleteFieldById(fieldId);
    }
}
