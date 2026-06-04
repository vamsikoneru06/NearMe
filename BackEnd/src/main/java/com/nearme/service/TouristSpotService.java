package com.nearme.service;

import com.nearme.dto.TouristSpotDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TouristSpotService {
    Page<TouristSpotDTO> findAll(String area, Pageable pageable);
    TouristSpotDTO findById(Long id);
}
