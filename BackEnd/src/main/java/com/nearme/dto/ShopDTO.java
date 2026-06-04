package com.nearme.dto;

import com.nearme.model.Shop;

public class ShopDTO {

    private Long id;
    private String name;
    private String category;
    private String location;
    private double rating;
    private String image;

    public static ShopDTO from(Shop shop) {
        ShopDTO dto = new ShopDTO();
        dto.id = shop.getId();
        dto.name = shop.getName();
        dto.category = shop.getCategory();
        dto.location = shop.getLocation();
        dto.rating = shop.getRating();
        dto.image = shop.getImage();
        return dto;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getLocation() { return location; }
    public double getRating() { return rating; }
    public String getImage() { return image; }
}
