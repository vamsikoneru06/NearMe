package com.nearme.dto;

import com.nearme.model.TouristSpot;

public class TouristSpotDTO {

    private Long id;
    private String name;
    private String area;
    private String image;
    private double rating;

    public static TouristSpotDTO from(TouristSpot spot) {
        TouristSpotDTO dto = new TouristSpotDTO();
        dto.id = spot.getId();
        dto.name = spot.getName();
        dto.area = spot.getArea();
        dto.image = spot.getImage();
        dto.rating = spot.getRating();
        return dto;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getArea() { return area; }
    public String getImage() { return image; }
    public double getRating() { return rating; }
}
