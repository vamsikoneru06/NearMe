package com.nearme.repository;

import com.nearme.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    /**
     * Fetch reviews with their User and Location eagerly to prevent
     * LazyInitializationException when mapping to ReviewDTO outside a session.
     */
    @EntityGraph(attributePaths = {"user", "location"})
    Page<Review> findByLocationId(Long locationId, Pageable pageable);

    @EntityGraph(attributePaths = {"user", "location"})
    List<Review> findByLocationId(Long locationId);

    @EntityGraph(attributePaths = {"user", "location"})
    Optional<Review> findByIdAndUserId(Long id, Long userId);

    boolean existsByUserIdAndLocationId(Long userId, Long locationId);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.location.id = :locationId")
    Double averageRatingByLocationId(@Param("locationId") Long locationId);
}
