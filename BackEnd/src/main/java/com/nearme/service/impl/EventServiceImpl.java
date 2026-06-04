package com.nearme.service.impl;

import com.nearme.dto.EventDTO;
import com.nearme.exception.ResourceNotFoundException;
import com.nearme.repository.EventRepository;
import com.nearme.service.EventService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Page<EventDTO> findAll(String location, Pageable pageable) {
        if (location != null && !location.isBlank()) {
            return eventRepository.findByVenueContainingIgnoreCase(location, pageable)
                    .map(EventDTO::from);
        }
        return eventRepository.findAll(pageable).map(EventDTO::from);
    }

    @Override
    public EventDTO findById(Long id) {
        return eventRepository.findById(id)
                .map(EventDTO::from)
                .orElseThrow(() -> new ResourceNotFoundException("Event", id));
    }
}
