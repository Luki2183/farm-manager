package pl.luki2183.farmManager.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.luki2183.farmManager.dto.GeoJSONDto;
import pl.luki2183.farmManager.mapper.FieldMapper;
import pl.luki2183.farmManager.service.FieldService;

import java.util.List;

@RestController
@RequestMapping("/api/geojsons")
@RequiredArgsConstructor
public class GeoJSONController {

    private final FieldService fieldService;
    private final FieldMapper fieldMapper;

    @GetMapping
    public List<GeoJSONDto> getGeoJSONs(){
        return fieldMapper.fieldsToGeoJSONDtoList(fieldService.getAllFields());
    }
}
