package com.nearme.dto;

import com.nearme.model.Category;
import com.nearme.model.Location;

import java.time.LocalDateTime;

public class LocationDTO {

    private Long id;
    private String name;
    private Category category;
    private String description;
    private String address;
    private double latitude;
    private double longitude;
    private double rating;
    private String imageUrl;
    private LocalDateTime createdAt;

    /**
     * Present only when returned from the /nearby endpoint.
     * Null for all other list/detail responses.
     */
    private Double distanceKm;

    // ── Factories ─────────────────────────────────────────────────────────────

    public static LocationDTO from(Location location) {
        return buildBase(location, null);
    }

    public static LocationDTO fromWithDistance(Location location, double distanceKm) {
        return buildBase(location, distanceKm);
    }

    private static LocationDTO buildBase(Location location, Double distanceKm) {
        LocationDTO dto = new LocationDTO();
        dto.id          = location.getId();
        dto.name        = location.getName();
        dto.category    = location.getCategory();
        dto.description = location.getDescription();
        dto.address     = location.getAddress();
        dto.latitude    = location.getLatitude();
        dto.longitude   = location.getLongitude();
        dto.rating      = location.getRating();
        dto.imageUrl    = location.getImageUrl();
        dto.createdAt   = location.getCreatedAt();
        dto.distanceKm  = distanceKm;
        return dto;
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    public Long getId()                  { return id; }
    public String getName()              { return name; }
    public Category getCategory()        { return category; }
    public String getDescription()       { return description; }
    public String getAddress()           { return address; }
    public double getLatitude()          { return latitude; }
    public double getLongitude()         { return longitude; }
    public double getRating()            { return rating; }
    public String getImageUrl()          { return imageUrl; }
    public LocalDateTime getCreatedAt()  { return createdAt; }
    public Double getDistanceKm()        { return distanceKm; }
}
