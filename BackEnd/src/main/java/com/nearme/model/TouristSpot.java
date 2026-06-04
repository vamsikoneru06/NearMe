package com.nearme.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tourist_spots")
public class TouristSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String area;
    private String image;
    private double rating;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }
}
