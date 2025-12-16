package pl.luki2183.farmManager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.luki2183.farmManager.dto.FieldDto;
import pl.luki2183.farmManager.dto.GeoJSONDto;
import pl.luki2183.farmManager.entity.FieldEntity;
import pl.luki2183.farmManager.mapper.FieldMapper;
import pl.luki2183.farmManager.service.FieldService;

import java.util.List;

@RestController
@RequestMapping("/api/fields")
@RequiredArgsConstructor
public class FieldController {

    private final FieldService fieldService;
    private final FieldMapper fieldMapper;

    @GetMapping
    public List<FieldDto> getAllFields(){
        return fieldMapper.fieldToDtoList(fieldService.getAllFields());
    }

    @PostMapping
    public FieldEntity addField(@RequestBody GeoJSONDto dto){
        return fieldService.saveField(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteField(@PathVariable String id){
        fieldService.deleteFieldById(id);
    }
}
