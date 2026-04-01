package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String area;
    @Lob
    @Column(columnDefinition="TEXT")
    private String image;
    private double rating;

    private String imageFilename; // ADDED THIS FIELD

    // Getters and Setters
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
    public String getImageFilename() { return imageFilename; } // ADDED THIS
    public void setImageFilename(String imageFilename) { this.imageFilename = imageFilename; } // ADDED THIS
}