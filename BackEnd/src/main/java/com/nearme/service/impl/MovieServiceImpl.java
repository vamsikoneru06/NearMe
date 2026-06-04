package com.nearme.service.impl;

import com.nearme.dto.MovieDTO;
import com.nearme.exception.ResourceNotFoundException;
import com.nearme.repository.MovieRepository;
import com.nearme.service.MovieService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Page<MovieDTO> findAll(String location, Pageable pageable) {
        if (location != null && !location.isBlank()) {
            return movieRepository.findByLocationContainingIgnoreCase(location, pageable)
                    .map(MovieDTO::from);
        }
        return movieRepository.findAll(pageable).map(MovieDTO::from);
    }

    @Override
    public MovieDTO findById(Long id) {
        return movieRepository.findById(id)
                .map(MovieDTO::from)
                .orElseThrow(() -> new ResourceNotFoundException("Movie", id));
    }
}
