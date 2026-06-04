package com.nearme.service;

import com.nearme.dto.EventDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventService {
    Page<EventDTO> findAll(String location, Pageable pageable);
    EventDTO findById(Long id);
}
