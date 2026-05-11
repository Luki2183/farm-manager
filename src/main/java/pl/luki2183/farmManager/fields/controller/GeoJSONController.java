package pl.luki2183.farmManager.fields.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.luki2183.farmManager.fields.dto.GeoJSONDto;
import pl.luki2183.farmManager.fields.dto.GeoJSONListDto;
import pl.luki2183.farmManager.fields.mapper.FieldMapper;
import pl.luki2183.farmManager.fields.service.FieldGetService;
import pl.luki2183.farmManager.fields.service.GeoJSONGetService;

import java.util.List;

/**
 * REST controller exposing GeoJSON endpoints.
 *
 * <p>Base path: {@code /api/geojsons}</p>
 *
 * <p>Returns field geometries in GeoJSON format, intended for consumption
 * by the frontend map component.</p>
 */
@Slf4j
@RestController
@RequestMapping("/api/geojsons")
@RequiredArgsConstructor
public class GeoJSONController {

    private final GeoJSONGetService getService;

    /**
     * Retrieves all fields as a GeoJSON feature collection.
     *
     * @return {@link ResponseEntity} containing a {@link GeoJSONListDto}
     *         of all fields serialized as GeoJSON features
     */
    @GetMapping
    public ResponseEntity<GeoJSONListDto> getGeoJSONs(){
        log.info("Received request to get all GeoJSONs");
        GeoJSONListDto result = getService.getAllGeoJSONs();
        log.info("Successfully retrieved all GeoJSONs: {}", result);
        return ResponseEntity.ok(result);
    }
}
