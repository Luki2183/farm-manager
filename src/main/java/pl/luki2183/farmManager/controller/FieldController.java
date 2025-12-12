package pl.luki2183.farmManager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.luki2183.farmManager.dto.FieldDto;
import pl.luki2183.farmManager.entity.FieldEntity;
import pl.luki2183.farmManager.service.FieldService;

import java.util.List;

@RestController
@RequestMapping("/api/fields")
@RequiredArgsConstructor
public class FieldController {

    private final FieldService fieldService;


    @GetMapping
    public List<FieldEntity> getAllFields(){
        return fieldService.getAllFields();
    }

    @PostMapping
    public FieldEntity addField(@RequestBody FieldDto dto){
        return fieldService.saveField(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteField(@PathVariable String id){
        fieldService.deleteFieldById(id);
    }
}
