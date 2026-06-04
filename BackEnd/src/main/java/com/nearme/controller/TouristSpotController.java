package com.nearme.controller;

import com.nearme.dto.TouristSpotDTO;
import com.nearme.service.TouristSpotService;
import com.nearme.util.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tourist-spots")
@Validated
@Tag(name = "Tourist Spots", description = "Tourist destinations and landmarks")
public class TouristSpotController {

    private final TouristSpotService touristSpotService;

    public TouristSpotController(TouristSpotService touristSpotService) {
        this.touristSpotService = touristSpotService;
    }

    @Operation(summary = "List tourist spots", description = "Returns paginated tourist spots, optionally filtered by city area")
    @ApiResponse(responseCode = "200", description = "Success")
    @GetMapping
    public ResponseEntity<PageResponse<TouristSpotDTO>> listTouristSpots(
            @RequestParam(required = false) String location,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size) {
        return ResponseEntity.ok(
                PageResponse.of(touristSpotService.findAll(location, PageRequest.of(page, size))));
    }

    @Operation(summary = "Get tourist spot by ID")
    @ApiResponse(responseCode = "200", description = "Tourist spot found")
    @ApiResponse(responseCode = "404", description = "Tourist spot not found")
    @GetMapping("/{id}")
    public ResponseEntity<TouristSpotDTO> getTouristSpot(@PathVariable Long id) {
        return ResponseEntity.ok(touristSpotService.findById(id));
    }
}
