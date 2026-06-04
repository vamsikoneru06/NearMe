package com.nearme.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String venue;
    private LocalDateTime date;
    private double price;
    private String image;

    @ElementCollection
    @CollectionTable(name = "event_slots")
    private List<TimeSlot> availableSlots = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getVenue() { return venue; }
    public void setVenue(String venue) { this.venue = venue; }
    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    public List<TimeSlot> getAvailableSlots() { return availableSlots; }
    public void setAvailableSlots(List<TimeSlot> slots) { this.availableSlots = slots; }
}
