package com.nearme.repository;

import com.nearme.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Page<Movie> findByLocationContainingIgnoreCase(String location, Pageable pageable);
}
