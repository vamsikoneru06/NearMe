package com.example.demo.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class TimeSlot {
    private String time; // e.g., "10:00 AM - 11:00 AM"
    private int availableSpots;

    public TimeSlot() {}

    public TimeSlot(String time, int availableSpots) {
        this.time = time;
        this.availableSpots = availableSpots;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getAvailableSpots() {
        return availableSpots;
    }

    public void setAvailableSpots(int availableSpots) {
        this.availableSpots = availableSpots;
    }
}