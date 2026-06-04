package com.nearme.controller;

import com.nearme.dto.ShopDTO;
import com.nearme.service.ShopService;
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
@RequestMapping("/api/shops")
@Validated
@Tag(name = "Shops", description = "Shopping destinations")
public class ShopController {

    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @Operation(summary = "List shops", description = "Returns paginated shops, optionally filtered by city name")
    @ApiResponse(responseCode = "200", description = "Success")
    @GetMapping
    public ResponseEntity<PageResponse<ShopDTO>> listShops(
            @RequestParam(required = false) String location,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size) {
        return ResponseEntity.ok(
                PageResponse.of(shopService.findAll(location, PageRequest.of(page, size))));
    }

    @Operation(summary = "Get shop by ID")
    @ApiResponse(responseCode = "200", description = "Shop found")
    @ApiResponse(responseCode = "404", description = "Shop not found")
    @GetMapping("/{id}")
    public ResponseEntity<ShopDTO> getShop(@PathVariable Long id) {
        return ResponseEntity.ok(shopService.findById(id));
    }
}
