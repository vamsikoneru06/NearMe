package com.nearme.dto;

import com.nearme.model.Category;
import com.nearme.model.Location;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Full location detail — returned by GET /api/locations/{id}.
 * Embeds the review list so the frontend can render them in a single request.
 */
public class LocationDetailDTO {

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
    private List<ReviewDTO> reviews;

    public static LocationDetailDTO from(Location location, List<ReviewDTO> reviews) {
        LocationDetailDTO dto = new LocationDetailDTO();
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
        dto.reviews     = reviews;
        return dto;
    }

    public Long getId()                   { return id; }
    public String getName()               { return name; }
    public Category getCategory()         { return category; }
    public String getDescription()        { return description; }
    public String getAddress()            { return address; }
    public double getLatitude()           { return latitude; }
    public double getLongitude()          { return longitude; }
    public double getRating()             { return rating; }
    public String getImageUrl()           { return imageUrl; }
    public LocalDateTime getCreatedAt()   { return createdAt; }
    public List<ReviewDTO> getReviews()   { return reviews; }
}
