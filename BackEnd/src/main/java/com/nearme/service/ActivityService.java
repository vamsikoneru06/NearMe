package com.nearme.service;

import com.nearme.dto.ActivityDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ActivityService {
    Page<ActivityDTO> findAll(String location, Pageable pageable);
    ActivityDTO findById(Long id);
}
