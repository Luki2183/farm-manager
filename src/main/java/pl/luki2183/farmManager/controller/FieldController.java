package pl.luki2183.farmManager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.luki2183.farmManager.dto.FieldDto;
import pl.luki2183.farmManager.dto.GeoJSONDto;
import pl.luki2183.farmManager.entity.FieldEntity;
import pl.luki2183.farmManager.mapper.FieldMapper;
import pl.luki2183.farmManager.service.FieldGetService;
import pl.luki2183.farmManager.service.FieldPostService;
import pl.luki2183.farmManager.service.FieldDeleteService;

import java.util.List;

@RestController
@RequestMapping("/api/fields")
@RequiredArgsConstructor
public class FieldController {

    private final FieldGetService getService;
    private final FieldPostService postService;
    private final FieldDeleteService deleteService;
    private final FieldMapper fieldMapper;

    @GetMapping
    public List<FieldDto> getAllFields(){
        return fieldMapper.fieldToDtoList(getService.getAllFields());
    }

    @PostMapping
    public FieldEntity addField(@RequestBody GeoJSONDto dto){
        return postService.saveField(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteField(@PathVariable String id){
        deleteService.deleteFieldById(id);
    }
}
