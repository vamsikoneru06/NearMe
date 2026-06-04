package com.nearme.dto;

import com.nearme.model.Favorite;

import java.time.LocalDateTime;

public class FavoriteDTO {

    private Long id;
    private LocationDTO location;
    private LocalDateTime savedAt;

    public static FavoriteDTO from(Favorite favorite) {
        FavoriteDTO dto = new FavoriteDTO();
        dto.id = favorite.getId();
        dto.location = LocationDTO.from(favorite.getLocation());
        dto.savedAt = favorite.getSavedAt();
        return dto;
    }

    public Long getId() { return id; }
    public LocationDTO getLocation() { return location; }
    public LocalDateTime getSavedAt() { return savedAt; }
}
