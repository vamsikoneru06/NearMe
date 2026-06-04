package com.nearme.service;

import com.nearme.dto.LocationDTO;
import com.nearme.dto.LocationDetailDTO;
import com.nearme.dto.LocationRequest;
import com.nearme.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LocationService {

    /** Paginated list, optionally filtered by category. */
    Page<LocationDTO> findAll(Category category, Pageable pageable);

    /** Full detail for one location — includes embedded reviews. */
    LocationDetailDTO findById(Long id);

    /**
     * Returns all locations within {@code radiusKm} kilometres of (lat, lng),
     * sorted nearest-first.  Distance is calculated with the Haversine formula.
     */
    List<LocationDTO> findNearby(double lat, double lng, double radiusKm);

    /** Full-text search on name and description. */
    Page<LocationDTO> search(String query, Pageable pageable);

    /** Create a new location (ADMIN). */
    LocationDTO create(LocationRequest request);

    /** Update an existing location (ADMIN). */
    LocationDTO update(Long id, LocationRequest request);

    /** Permanently remove a location (ADMIN). */
    void delete(Long id);
}
