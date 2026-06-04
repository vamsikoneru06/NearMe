package com.nearme.controller;

import com.nearme.dto.GooglePlaceDTO;
import com.nearme.service.GooglePlacesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/places")
public class GooglePlacesController {

    private final GooglePlacesService googlePlacesService;

    public GooglePlacesController(GooglePlacesService googlePlacesService) {
        this.googlePlacesService = googlePlacesService;
    }

    @GetMapping
    public ResponseEntity<?> search(
            @RequestParam String city,
            @RequestParam(defaultValue = "tourist attraction") String type) {

        if (city == null || city.isBlank() || city.length() > 100)
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid city parameter"));

        if (type.length() > 100)
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid type parameter"));

        if (!googlePlacesService.isAvailable())
            return ResponseEntity.status(503)
                .body(Map.of("error", "Google Places API key not configured"));

        List<GooglePlaceDTO> results = googlePlacesService.search(city, type);
        return ResponseEntity.ok(results);
    }
}
