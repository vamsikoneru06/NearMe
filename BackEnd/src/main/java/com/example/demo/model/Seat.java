package com.example.demo.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Seat {
    private String seatRow;
    private int seatNumber;
    private String status; // e.g., "AVAILABLE", "BOOKED"

    public Seat() {}

    public Seat(String seatRow, int seatNumber, String status) {
        this.seatRow = seatRow;
        this.seatNumber = seatNumber;
        this.status = status;
    }

    public String getSeatRow() {
        return seatRow;
    }

    public void setSeatRow(String seatRow) {
        this.seatRow = seatRow;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}