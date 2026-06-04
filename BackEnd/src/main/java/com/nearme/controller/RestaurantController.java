package com.nearme.controller;

import com.nearme.dto.RestaurantDTO;
import com.nearme.service.RestaurantService;
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
@RequestMapping("/api/restaurants")
@Validated
@Tag(name = "Restaurants", description = "Restaurant listings and menus")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @Operation(summary = "List restaurants", description = "Returns paginated restaurants, optionally filtered by city name")
    @ApiResponse(responseCode = "200", description = "Success")
    @GetMapping
    public ResponseEntity<PageResponse<RestaurantDTO>> listRestaurants(
            @RequestParam(required = false) String location,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size) {
        return ResponseEntity.ok(
                PageResponse.of(restaurantService.findAll(location, PageRequest.of(page, size))));
    }

    @Operation(summary = "Get restaurant by ID")
    @ApiResponse(responseCode = "200", description = "Restaurant found")
    @ApiResponse(responseCode = "404", description = "Restaurant not found")
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDTO> getRestaurant(@PathVariable Long id) {
        return ResponseEntity.ok(restaurantService.findById(id));
    }
}
