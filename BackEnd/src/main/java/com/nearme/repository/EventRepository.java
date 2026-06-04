package com.nearme.repository;

import com.nearme.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
    Page<Event> findByVenueContainingIgnoreCase(String venue, Pageable pageable);
}
