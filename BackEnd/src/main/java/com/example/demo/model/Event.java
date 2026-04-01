package com.example.demo.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String venue;
    private Date date;
    private double price;
    @Lob
    @Column(columnDefinition="TEXT")
    private String image;
    private String imageFilename;

    @ElementCollection
    @CollectionTable(name = "event_slots")
    private List<TimeSlot> availableSlots = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getVenue() { return venue; }
    public void setVenue(String venue) { this.venue = venue; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    public String getImageFilename() { return imageFilename; }
    public void setImageFilename(String imageFilename) { this.imageFilename = imageFilename; }
    public List<TimeSlot> getAvailableSlots() { return availableSlots; }
    public void setAvailableSlots(List<TimeSlot> availableSlots) { this.availableSlots = availableSlots; }
}