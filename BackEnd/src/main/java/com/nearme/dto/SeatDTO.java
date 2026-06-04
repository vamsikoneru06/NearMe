package com.nearme.dto;

import com.nearme.model.Seat;

public class SeatDTO {

    private String seatRow;
    private int seatNumber;
    private String status;

    public static SeatDTO from(Seat seat) {
        SeatDTO dto = new SeatDTO();
        dto.seatRow = seat.getSeatRow();
        dto.seatNumber = seat.getSeatNumber();
        dto.status = seat.getStatus();
        return dto;
    }

    public String getSeatRow() { return seatRow; }
    public int getSeatNumber() { return seatNumber; }
    public String getStatus() { return status; }
}
