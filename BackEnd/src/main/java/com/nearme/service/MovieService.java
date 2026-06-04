package com.nearme.service;

import com.nearme.dto.MovieDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieService {
    Page<MovieDTO> findAll(String location, Pageable pageable);
    MovieDTO findById(Long id);
}
