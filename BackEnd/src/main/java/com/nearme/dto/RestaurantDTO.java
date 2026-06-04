package com.nearme.dto;

import com.nearme.model.Restaurant;

import java.util.List;
import java.util.stream.Collectors;

public class RestaurantDTO {

    private Long id;
    private String name;
    private String type;
    private String location;
    private double rating;
    private String image;
    private List<MenuItemDTO> menu;

    public static RestaurantDTO from(Restaurant r) {
        RestaurantDTO dto = new RestaurantDTO();
        dto.id = r.getId();
        dto.name = r.getName();
        dto.type = r.getType();
        dto.location = r.getLocation();
        dto.rating = r.getRating();
        dto.image = r.getImage();
        dto.menu = r.getMenu().stream()
                .map(MenuItemDTO::from)
                .collect(Collectors.toList());
        return dto;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
    public String getLocation() { return location; }
    public double getRating() { return rating; }
    public String getImage() { return image; }
    public List<MenuItemDTO> getMenu() { return menu; }
}
