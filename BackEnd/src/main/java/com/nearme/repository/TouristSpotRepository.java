package com.nearme.repository;

import com.nearme.model.TouristSpot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TouristSpotRepository extends JpaRepository<TouristSpot, Long> {
    Page<TouristSpot> findByAreaContainingIgnoreCase(String area, Pageable pageable);
}
