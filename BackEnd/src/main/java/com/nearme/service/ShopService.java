package com.nearme.service;

import com.nearme.dto.ShopDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShopService {
    Page<ShopDTO> findAll(String location, Pageable pageable);
    ShopDTO findById(Long id);
}
