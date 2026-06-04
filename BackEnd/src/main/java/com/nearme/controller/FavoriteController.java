package com.nearme.controller;

import com.nearme.dto.FavoriteDTO;
import com.nearme.service.FavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@Tag(name = "Favorites", description = "Save and manage your favourite locations")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    // ── GET /api/favorites  ───────────────────────────────────────────────────

    @Operation(summary = "List my saved locations",
               security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Success")
    @ApiResponse(responseCode = "401", description = "Not authenticated")
    @GetMapping
    public ResponseEntity<List<FavoriteDTO>> getFavorites(
            @AuthenticationPrincipal UserDetails userDetails) {

        return ResponseEntity.ok(
                favoriteService.getFavorites(userDetails.getUsername()));
    }

    // ── POST /api/favorites/{locationId}  ─────────────────────────────────────

    @Operation(summary = "Save a location to favorites",
               security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Location saved")
    @ApiResponse(responseCode = "400", description = "Already in favorites")
    @ApiResponse(responseCode = "401", description = "Not authenticated")
    @ApiResponse(responseCode = "404", description = "Location not found")
    @PostMapping("/{locationId}")
    public ResponseEntity<FavoriteDTO> addFavorite(
            @PathVariable Long locationId,
            @AuthenticationPrincipal UserDetails userDetails) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(favoriteService.addFavorite(locationId,
                        userDetails.getUsername()));
    }

    // ── DELETE /api/favorites/{locationId}  ───────────────────────────────────

    @Operation(summary = "Remove a location from favorites",
               security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "204", description = "Removed from favorites")
    @ApiResponse(responseCode = "401", description = "Not authenticated")
    @ApiResponse(responseCode = "404", description = "Favorite not found")
    @DeleteMapping("/{locationId}")
    public ResponseEntity<Void> removeFavorite(
            @PathVariable Long locationId,
            @AuthenticationPrincipal UserDetails userDetails) {

        favoriteService.removeFavorite(locationId, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}
