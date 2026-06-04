package com.nearme.controller;

import com.nearme.dto.ActivityDTO;
import com.nearme.service.ActivityService;
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
@RequestMapping("/api/activities")
@Validated
@Tag(name = "Activities", description = "Local activities and experiences")
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @Operation(summary = "List activities", description = "Returns paginated activities, optionally filtered by city name")
    @ApiResponse(responseCode = "200", description = "Success")
    @GetMapping
    public ResponseEntity<PageResponse<ActivityDTO>> listActivities(
            @RequestParam(required = false) String location,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size) {
        return ResponseEntity.ok(
                PageResponse.of(activityService.findAll(location, PageRequest.of(page, size))));
    }

    @Operation(summary = "Get activity by ID")
    @ApiResponse(responseCode = "200", description = "Activity found")
    @ApiResponse(responseCode = "404", description = "Activity not found")
    @GetMapping("/{id}")
    public ResponseEntity<ActivityDTO> getActivity(@PathVariable Long id) {
        return ResponseEntity.ok(activityService.findById(id));
    }
}
