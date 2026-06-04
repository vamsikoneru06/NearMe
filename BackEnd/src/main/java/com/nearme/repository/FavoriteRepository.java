package com.nearme.repository;

import com.nearme.model.Favorite;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    /**
     * Eagerly fetch Location so FavoriteDTO.from() can map it without
     * requiring an open session.
     */
    @EntityGraph(attributePaths = {"location"})
    List<Favorite> findByUserId(Long userId);

    @EntityGraph(attributePaths = {"location"})
    Optional<Favorite> findByUserIdAndLocationId(Long userId, Long locationId);

    boolean existsByUserIdAndLocationId(Long userId, Long locationId);

    @Transactional
    void deleteByUserIdAndLocationId(Long userId, Long locationId);
}
