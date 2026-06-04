package com.nearme.service;

import com.nearme.dto.RestaurantDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RestaurantService {
    Page<RestaurantDTO> findAll(String location, Pageable pageable);
    RestaurantDTO findById(Long id);
}
