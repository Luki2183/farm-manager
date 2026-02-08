package pl.luki2183.farmManager.fields.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.luki2183.farmManager.fields.dto.GeoJSONDto;
import pl.luki2183.farmManager.fields.mapper.FieldMapper;
import pl.luki2183.farmManager.fields.service.FieldGetService;
import pl.luki2183.farmManager.fields.service.GeoJSONGetService;

import java.util.List;

@RestController
@RequestMapping("/api/geojsons")
@RequiredArgsConstructor
public class GeoJSONController {

    private final GeoJSONGetService getService;

    //todo add response status

    @GetMapping
    public List<GeoJSONDto> getGeoJSONs(){
        return getService.getAllGeoJSONs();
    }
}
