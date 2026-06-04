package com.nearme.service.impl;

import com.nearme.dto.FavoriteDTO;
import com.nearme.exception.ResourceNotFoundException;
import com.nearme.exception.ValidationException;
import com.nearme.model.Favorite;
import com.nearme.model.Location;
import com.nearme.model.User;
import com.nearme.repository.FavoriteRepository;
import com.nearme.repository.LocationRepository;
import com.nearme.repository.UserRepository;
import com.nearme.service.FavoriteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final LocationRepository locationRepository;
    private final UserRepository     userRepository;

    public FavoriteServiceImpl(FavoriteRepository favoriteRepository,
                               LocationRepository locationRepository,
                               UserRepository userRepository) {
        this.favoriteRepository = favoriteRepository;
        this.locationRepository = locationRepository;
        this.userRepository     = userRepository;
    }

    // ── Save ──────────────────────────────────────────────────────────────────

    @Override
    public FavoriteDTO addFavorite(Long locationId, String userEmail) {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new ResourceNotFoundException("Location", locationId));

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userEmail));

        if (favoriteRepository.existsByUserIdAndLocationId(user.getId(), locationId)) {
            throw new ValidationException("Location is already in your favorites");
        }

        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setLocation(location);
        return FavoriteDTO.from(favoriteRepository.save(favorite));
    }

    // ── Remove ────────────────────────────────────────────────────────────────

    @Override
    public void removeFavorite(Long locationId, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userEmail));

        if (!favoriteRepository.existsByUserIdAndLocationId(user.getId(), locationId)) {
            throw new ResourceNotFoundException(
                    "Favorite not found for location id " + locationId);
        }

        favoriteRepository.deleteByUserIdAndLocationId(user.getId(), locationId);
    }

    // ── List ──────────────────────────────────────────────────────────────────

    @Override
    @Transactional(readOnly = true)
    public List<FavoriteDTO> getFavorites(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userEmail));

        return favoriteRepository.findByUserId(user.getId()).stream()
                .map(FavoriteDTO::from)
                .collect(Collectors.toList());
    }
}
