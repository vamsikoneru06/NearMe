package com.nearme.service.impl;

import com.nearme.dto.LocationDTO;
import com.nearme.dto.LocationDetailDTO;
import com.nearme.dto.LocationRequest;
import com.nearme.dto.ReviewDTO;
import com.nearme.exception.ResourceNotFoundException;
import com.nearme.model.Category;
import com.nearme.model.Location;
import com.nearme.repository.LocationRepository;
import com.nearme.repository.ReviewRepository;
import com.nearme.service.LocationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl implements LocationService {

    private static final double EARTH_RADIUS_KM = 6371.0;

    private final LocationRepository locationRepository;
    private final ReviewRepository reviewRepository;

    public LocationServiceImpl(LocationRepository locationRepository,
                               ReviewRepository reviewRepository) {
        this.locationRepository = locationRepository;
        this.reviewRepository   = reviewRepository;
    }

    // ── Read ──────────────────────────────────────────────────────────────────

    @Override
    public Page<LocationDTO> findAll(Category category, Pageable pageable) {
        if (category != null) {
            return locationRepository.findByCategory(category, pageable)
                    .map(LocationDTO::from);
        }
        return locationRepository.findAll(pageable).map(LocationDTO::from);
    }

    @Override
    @Transactional(readOnly = true)
    public LocationDetailDTO findById(Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Location", id));

        List<ReviewDTO> reviews = reviewRepository.findByLocationId(id).stream()
                .map(ReviewDTO::from)
                .collect(Collectors.toList());

        return LocationDetailDTO.from(location, reviews);
    }

    @Override
    public List<LocationDTO> findNearby(double lat, double lng, double radiusKm) {
        // Step 1 — bounding box pre-filter to avoid a full-table scan
        double latDelta = radiusKm / 111.0;
        double lngDelta = radiusKm / (111.0 * Math.cos(Math.toRadians(lat)));

        List<Location> candidates = locationRepository.findWithinBoundingBox(
                lat - latDelta, lat + latDelta,
                lng - lngDelta, lng + lngDelta);

        // Step 2 — precise Haversine filter + sort nearest-first
        return candidates.stream()
                .map(loc -> {
                    double dist = haversineDistanceKm(
                            lat, lng, loc.getLatitude(), loc.getLongitude());
                    return LocationDTO.fromWithDistance(loc, dist);
                })
                .filter(dto -> dto.getDistanceKm() <= radiusKm)
                .sorted(Comparator.comparingDouble(LocationDTO::getDistanceKm))
                .collect(Collectors.toList());
    }

    @Override
    public Page<LocationDTO> search(String query, Pageable pageable) {
        return locationRepository
                .findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                        query, query, pageable)
                .map(LocationDTO::from);
    }

    // ── Write ─────────────────────────────────────────────────────────────────

    @Override
    public LocationDTO create(LocationRequest request) {
        Location location = new Location();
        applyRequest(location, request);
        return LocationDTO.from(locationRepository.save(location));
    }

    @Override
    public LocationDTO update(Long id, LocationRequest request) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Location", id));
        applyRequest(location, request);
        return LocationDTO.from(locationRepository.save(location));
    }

    @Override
    public void delete(Long id) {
        if (!locationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Location", id);
        }
        locationRepository.deleteById(id);
    }

    // ── Private helpers ───────────────────────────────────────────────────────

    private void applyRequest(Location location, LocationRequest req) {
        location.setName(req.getName());
        location.setCategory(req.getCategory());
        location.setDescription(req.getDescription());
        location.setAddress(req.getAddress());
        location.setLatitude(req.getLatitude());
        location.setLongitude(req.getLongitude());
        location.setImageUrl(req.getImageUrl() != null ? req.getImageUrl() : "");
    }

    /**
     * Haversine formula — returns the great-circle distance in kilometres
     * between two points on the Earth's surface.
     *
     * @param lat1 latitude  of point A (degrees)
     * @param lon1 longitude of point A (degrees)
     * @param lat2 latitude  of point B (degrees)
     * @param lon2 longitude of point B (degrees)
     * @return distance in kilometres
     */
    static double haversineDistanceKm(double lat1, double lon1,
                                      double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        return EARTH_RADIUS_KM * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }
}
