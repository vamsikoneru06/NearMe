package com.nearme.service.impl;

import com.nearme.dto.ActivityDTO;
import com.nearme.exception.ResourceNotFoundException;
import com.nearme.repository.ActivityRepository;
import com.nearme.service.ActivityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityServiceImpl(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public Page<ActivityDTO> findAll(String location, Pageable pageable) {
        if (location != null && !location.isBlank()) {
            return activityRepository.findByPlaceContainingIgnoreCase(location, pageable)
                    .map(ActivityDTO::from);
        }
        return activityRepository.findAll(pageable).map(ActivityDTO::from);
    }

    @Override
    public ActivityDTO findById(Long id) {
        return activityRepository.findById(id)
                .map(ActivityDTO::from)
                .orElseThrow(() -> new ResourceNotFoundException("Activity", id));
    }
}
