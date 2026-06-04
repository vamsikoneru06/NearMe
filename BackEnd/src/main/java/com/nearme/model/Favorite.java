package com.nearme.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "favorites",
       uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "location_id"},
               name = "uq_favorite_user_location"))
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @Column(nullable = false, updatable = false)
    private LocalDateTime savedAt;

    @PrePersist
    protected void onCreate() {
        this.savedAt = LocalDateTime.now();
    }

    // ── Getters / Setters ─────────────────────────────────────────────────────

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }
    public LocalDateTime getSavedAt() { return savedAt; }
}
