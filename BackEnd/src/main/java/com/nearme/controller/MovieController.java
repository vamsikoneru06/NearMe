package com.nearme.controller;

import com.nearme.dto.MovieDTO;
import com.nearme.service.MovieService;
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
@RequestMapping("/api/movies")
@Validated
@Tag(name = "Movies", description = "Movie listings and showtimes")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @Operation(summary = "List movies", description = "Returns paginated movies, optionally filtered by city name")
    @ApiResponse(responseCode = "200", description = "Success")
    @GetMapping
    public ResponseEntity<PageResponse<MovieDTO>> listMovies(
            @RequestParam(required = false) String location,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size) {
        return ResponseEntity.ok(
                PageResponse.of(movieService.findAll(location, PageRequest.of(page, size))));
    }

    @Operation(summary = "Get movie by ID")
    @ApiResponse(responseCode = "200", description = "Movie found")
    @ApiResponse(responseCode = "404", description = "Movie not found")
    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> getMovie(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.findById(id));
    }
}
