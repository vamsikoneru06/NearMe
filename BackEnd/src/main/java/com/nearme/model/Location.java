package com.nearme.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Unified point-of-interest entity — replaces the old per-category models.
 * Stores geolocation data (lat/lng) to support the nearby-search feature.
 */
@Entity
@Table(name = "locations", indexes = {
        @Index(name = "idx_location_category", columnList = "category"),
        @Index(name = "idx_location_lat",      columnList = "latitude"),
        @Index(name = "idx_location_lng",      columnList = "longitude")
})
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Category category;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String address;

    private double latitude;
    private double longitude;

    @Column(nullable = false)
    private double rating = 0.0;

    private String imageUrl;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // ── Getters / Setters ─────────────────────────────────────────────────────

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }
    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
