package com.nearme.service.impl;

import com.nearme.dto.RestaurantDTO;
import com.nearme.exception.ResourceNotFoundException;
import com.nearme.repository.RestaurantRepository;
import com.nearme.service.RestaurantService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Page<RestaurantDTO> findAll(String location, Pageable pageable) {
        if (location != null && !location.isBlank()) {
            return restaurantRepository.findByLocationContainingIgnoreCase(location, pageable)
                    .map(RestaurantDTO::from);
        }
        return restaurantRepository.findAll(pageable).map(RestaurantDTO::from);
    }

    @Override
    public RestaurantDTO findById(Long id) {
        return restaurantRepository.findById(id)
                .map(RestaurantDTO::from)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant", id));
    }
}
