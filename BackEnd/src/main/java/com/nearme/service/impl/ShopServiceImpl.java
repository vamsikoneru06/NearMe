package com.nearme.service.impl;

import com.nearme.dto.ShopDTO;
import com.nearme.exception.ResourceNotFoundException;
import com.nearme.repository.ShopRepository;
import com.nearme.service.ShopService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;

    public ShopServiceImpl(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @Override
    public Page<ShopDTO> findAll(String location, Pageable pageable) {
        if (location != null && !location.isBlank()) {
            return shopRepository.findByLocationContainingIgnoreCase(location, pageable)
                    .map(ShopDTO::from);
        }
        return shopRepository.findAll(pageable).map(ShopDTO::from);
    }

    @Override
    public ShopDTO findById(Long id) {
        return shopRepository.findById(id)
                .map(ShopDTO::from)
                .orElseThrow(() -> new ResourceNotFoundException("Shop", id));
    }
}
