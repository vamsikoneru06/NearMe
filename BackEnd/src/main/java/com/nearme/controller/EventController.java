package com.nearme.controller;

import com.nearme.dto.EventDTO;
import com.nearme.service.EventService;
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
@RequestMapping("/api/events")
@Validated
@Tag(name = "Events", description = "Upcoming events and festivals")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @Operation(summary = "List events", description = "Returns paginated events, optionally filtered by city name")
    @ApiResponse(responseCode = "200", description = "Success")
    @GetMapping
    public ResponseEntity<PageResponse<EventDTO>> listEvents(
            @RequestParam(required = false) String location,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size) {
        return ResponseEntity.ok(
                PageResponse.of(eventService.findAll(location, PageRequest.of(page, size))));
    }

    @Operation(summary = "Get event by ID")
    @ApiResponse(responseCode = "200", description = "Event found")
    @ApiResponse(responseCode = "404", description = "Event not found")
    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEvent(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.findById(id));
    }
}
