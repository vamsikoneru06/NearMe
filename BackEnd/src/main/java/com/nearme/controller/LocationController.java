package com.nearme.controller;

import com.nearme.dto.LocationDTO;
import com.nearme.dto.LocationDetailDTO;
import com.nearme.dto.LocationRequest;
import com.nearme.model.Category;
import com.nearme.service.LocationService;
import com.nearme.util.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
@Validated
@Tag(name = "Locations", description = "Unified point-of-interest API with geolocation support")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    // ── Read endpoints ────────────────────────────────────────────────────────

    @Operation(summary = "List all locations",
               description = "Paginated list of all locations, optionally filtered by category")
    @ApiResponse(responseCode = "200", description = "Success")
    @GetMapping
    public ResponseEntity<PageResponse<LocationDTO>> listLocations(
            @RequestParam(required = false) Category category,
            @RequestParam(defaultValue = "0")  @Min(0)         int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size) {

        return ResponseEntity.ok(
                PageResponse.of(locationService.findAll(category, PageRequest.of(page, size))));
    }

    @Operation(summary = "Find nearby locations",
               description = "Returns locations within the specified radius (km) sorted nearest-first. "
                           + "Uses the Haversine formula for accurate great-circle distance.")
    @ApiResponse(responseCode = "200", description = "Success")
    @GetMapping("/nearby")
    public ResponseEntity<List<LocationDTO>> findNearby(
            @RequestParam @DecimalMin("-90.0")  @DecimalMax("90.0")  double lat,
            @RequestParam @DecimalMin("-180.0") @DecimalMax("180.0") double lng,
            @RequestParam(defaultValue = "5.0") @DecimalMin("0.1") @DecimalMax("500.0") double radius) {

        return ResponseEntity.ok(locationService.findNearby(lat, lng, radius));
    }

    @Operation(summary = "Full-text search", description = "Searches name and description fields")
    @ApiResponse(responseCode = "200", description = "Success")
    @GetMapping("/search")
    public ResponseEntity<PageResponse<LocationDTO>> search(
            @RequestParam @NotBlank String q,
            @RequestParam(defaultValue = "0")  @Min(0)           int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size) {

        return ResponseEntity.ok(
                PageResponse.of(locationService.search(q, PageRequest.of(page, size))));
    }

    @Operation(summary = "Get location by ID",
               description = "Returns full detail including embedded reviews")
    @ApiResponse(responseCode = "200", description = "Location found")
    @ApiResponse(responseCode = "404", description = "Location not found")
    @GetMapping("/{id}")
    public ResponseEntity<LocationDetailDTO> getLocation(@PathVariable Long id) {
        return ResponseEntity.ok(locationService.findById(id));
    }

    // ── Admin write endpoints ─────────────────────────────────────────────────

    @Operation(summary = "Create a location (ADMIN only)",
               security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Location created")
    @ApiResponse(responseCode = "400", description = "Validation error")
    @ApiResponse(responseCode = "401", description = "Not authenticated")
    @ApiResponse(responseCode = "403", description = "Admin role required")
    @PostMapping
    public ResponseEntity<LocationDTO> createLocation(
            @Valid @RequestBody LocationRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(locationService.create(request));
    }

    @Operation(summary = "Update a location (ADMIN only)",
               security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Location updated")
    @ApiResponse(responseCode = "404", description = "Location not found")
    @PutMapping("/{id}")
    public ResponseEntity<LocationDTO> updateLocation(
            @PathVariable Long id,
            @Valid @RequestBody LocationRequest request) {

        return ResponseEntity.ok(locationService.update(id, request));
    }

    @Operation(summary = "Delete a location (ADMIN only)",
               security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "204", description = "Location deleted")
    @ApiResponse(responseCode = "404", description = "Location not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        locationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
