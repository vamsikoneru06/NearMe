package com.nearme.repository;

import com.nearme.model.Category;
import com.nearme.model.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {

    Page<Location> findByCategory(Category category, Pageable pageable);

    Page<Location> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String name, String description, Pageable pageable);

    /**
     * Returns all locations whose coordinates fall within a bounding box.
     * The caller (LocationService) applies the precise Haversine filter.
     */
    @Query("SELECT l FROM Location l WHERE " +
           "l.latitude  BETWEEN :minLat AND :maxLat AND " +
           "l.longitude BETWEEN :minLng AND :maxLng")
    List<Location> findWithinBoundingBox(
            @Param("minLat") double minLat, @Param("maxLat") double maxLat,
            @Param("minLng") double minLng, @Param("maxLng") double maxLng);
}
