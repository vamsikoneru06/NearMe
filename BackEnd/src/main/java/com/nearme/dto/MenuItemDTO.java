package com.nearme.dto;

import com.nearme.model.MenuItem;

public class MenuItemDTO {

    private String dishName;
    private double dishPrice;
    private boolean popular;

    public static MenuItemDTO from(MenuItem item) {
        MenuItemDTO dto = new MenuItemDTO();
        dto.dishName = item.getDishName();
        dto.dishPrice = item.getDishPrice();
        dto.popular = item.isPopular();
        return dto;
    }

    public String getDishName() { return dishName; }
    public double getDishPrice() { return dishPrice; }
    public boolean isPopular() { return popular; }
}
