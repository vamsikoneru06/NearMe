package com.nearme.controller;

import com.nearme.dto.ReviewDTO;
import com.nearme.dto.ReviewRequest;
import com.nearme.service.ReviewService;
import com.nearme.util.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@Tag(name = "Reviews", description = "Submit and browse location reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // ── GET /api/locations/{locationId}/reviews  (public) ─────────────────────

    @Operation(summary = "List reviews for a location",
               description = "Returns paginated reviews, newest first")
    @ApiResponse(responseCode = "200", description = "Success")
    @ApiResponse(responseCode = "404", description = "Location not found")
    @GetMapping("/api/locations/{locationId}/reviews")
    public ResponseEntity<PageResponse<ReviewDTO>> getReviews(
            @PathVariable Long locationId,
            @RequestParam(defaultValue = "0")  @Min(0)           int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size) {

        return ResponseEntity.ok(
                PageResponse.of(reviewService.getReviews(locationId,
                        PageRequest.of(page, size))));
    }

    // ── POST /api/locations/{locationId}/reviews  (authenticated) ─────────────

    @Operation(summary = "Add a review",
               description = "Authenticated users may leave one review per location. "
                           + "Location rating is recalculated automatically.",
               security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Review created")
    @ApiResponse(responseCode = "400", description = "Validation error or duplicate review")
    @ApiResponse(responseCode = "401", description = "Not authenticated")
    @ApiResponse(responseCode = "404", description = "Location not found")
    @PostMapping("/api/locations/{locationId}/reviews")
    public ResponseEntity<ReviewDTO> addReview(
            @PathVariable Long locationId,
            @Valid @RequestBody ReviewRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reviewService.addReview(locationId, request,
                        userDetails.getUsername()));
    }

    // ── DELETE /api/reviews/{id}  (owner only) ────────────────────────────────

    @Operation(summary = "Delete your own review",
               description = "Only the author may delete a review.",
               security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "204", description = "Review deleted")
    @ApiResponse(responseCode = "401", description = "Not authenticated")
    @ApiResponse(responseCode = "404", description = "Review not found or not owned by you")
    @DeleteMapping("/api/reviews/{id}")
    public ResponseEntity<Void> deleteReview(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {

        reviewService.deleteReview(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}
