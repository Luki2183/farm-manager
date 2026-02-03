package pl.luki2183.farmManager.fields.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.luki2183.farmManager.fields.dto.FieldDto;
import pl.luki2183.farmManager.fields.dto.GeoJSONDto;
import pl.luki2183.farmManager.fields.entity.FieldEntity;
import pl.luki2183.farmManager.fields.mapper.FieldMapper;
import pl.luki2183.farmManager.fields.service.FieldGetService;
import pl.luki2183.farmManager.fields.service.FieldPostService;
import pl.luki2183.farmManager.fields.service.FieldDeleteService;

import java.util.List;

@RestController
@RequestMapping("/api/fields")
@RequiredArgsConstructor
public class FieldController {

    private final FieldGetService getService;
    private final FieldPostService postService;
    private final FieldDeleteService deleteService;

    @GetMapping
    public List<FieldDto> getAllFields(){
        return getService.getAllFields();
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
