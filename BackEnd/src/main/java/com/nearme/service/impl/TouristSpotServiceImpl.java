package com.nearme.service.impl;

import com.nearme.dto.TouristSpotDTO;
import com.nearme.exception.ResourceNotFoundException;
import com.nearme.repository.TouristSpotRepository;
import com.nearme.service.TouristSpotService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TouristSpotServiceImpl implements TouristSpotService {

    private final TouristSpotRepository touristSpotRepository;

    public TouristSpotServiceImpl(TouristSpotRepository touristSpotRepository) {
        this.touristSpotRepository = touristSpotRepository;
    }

    @Override
    public Page<TouristSpotDTO> findAll(String area, Pageable pageable) {
        if (area != null && !area.isBlank()) {
            return touristSpotRepository.findByAreaContainingIgnoreCase(area, pageable)
                    .map(TouristSpotDTO::from);
        }
        return touristSpotRepository.findAll(pageable).map(TouristSpotDTO::from);
    }

    @Override
    public TouristSpotDTO findById(Long id) {
        return touristSpotRepository.findById(id)
                .map(TouristSpotDTO::from)
                .orElseThrow(() -> new ResourceNotFoundException("TouristSpot", id));
    }
}
