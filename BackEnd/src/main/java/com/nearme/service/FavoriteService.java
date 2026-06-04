package com.nearme.service;

import com.nearme.dto.FavoriteDTO;

import java.util.List;

public interface FavoriteService {

    /** Save a location to the authenticated user's favorites. */
    FavoriteDTO addFavorite(Long locationId, String userEmail);

    /** Remove a location from the authenticated user's favorites. */
    void removeFavorite(Long locationId, String userEmail);

    /** List all saved locations for the authenticated user. */
    List<FavoriteDTO> getFavorites(String userEmail);
}
