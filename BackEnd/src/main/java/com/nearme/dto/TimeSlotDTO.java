package com.nearme.dto;

import com.nearme.model.TimeSlot;

public class TimeSlotDTO {

    private String time;
    private int availableSpots;

    public static TimeSlotDTO from(TimeSlot slot) {
        TimeSlotDTO dto = new TimeSlotDTO();
        dto.time = slot.getTime();
        dto.availableSpots = slot.getAvailableSpots();
        return dto;
    }

    public String getTime() { return time; }
    public int getAvailableSpots() { return availableSpots; }
}
