package com.example.demo.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class MenuItem {
    private String dishName;
    private double dishPrice;
    private boolean isPopular;

    public MenuItem() {}

    public MenuItem(String dishName, double dishPrice, boolean isPopular) {
        this.dishName = dishName;
        this.dishPrice = dishPrice;
        this.isPopular = isPopular;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public double getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(double dishPrice) {
        this.dishPrice = dishPrice;
    }

    public boolean isPopular() {
        return isPopular;
    }

    public void setPopular(boolean popular) {
        isPopular = popular;
    }
}