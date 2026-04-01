package com.example.demo.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String place;
    private double cost;
    private double rating;
    @Lob
    @Column(columnDefinition="TEXT")
    private String image;
    private String imageFilename;

    @ElementCollection
    @CollectionTable(name = "activity_slots")
    private List<TimeSlot> availableSlots = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPlace() { return place; }
    public void setPlace(String place) { this.place = place; }
    public double getCost() { return cost; }
    public void setCost(double cost) { this.cost = cost; }
    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    public String getImageFilename() { return imageFilename; }
    public void setImageFilename(String imageFilename) { this.imageFilename = imageFilename; }
    public List<TimeSlot> getAvailableSlots() { return availableSlots; }
    public void setAvailableSlots(List<TimeSlot> availableSlots) { this.availableSlots = availableSlots; }
}