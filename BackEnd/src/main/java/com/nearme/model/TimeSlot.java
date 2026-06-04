package com.nearme.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class TimeSlot {

    private String time;
    private int availableSpots;

    public TimeSlot() {}

    public TimeSlot(String time, int availableSpots) {
        this.time = time;
        this.availableSpots = availableSpots;
    }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
    public int getAvailableSpots() { return availableSpots; }
    public void setAvailableSpots(int availableSpots) { this.availableSpots = availableSpots; }
}
