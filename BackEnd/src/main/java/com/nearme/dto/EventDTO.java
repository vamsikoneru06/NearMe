package com.nearme.dto;

import com.nearme.model.Event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class EventDTO {

    private Long id;
    private String name;
    private String venue;
    private LocalDateTime date;
    private double price;
    private String image;
    private List<TimeSlotDTO> availableSlots;

    public static EventDTO from(Event event) {
        EventDTO dto = new EventDTO();
        dto.id = event.getId();
        dto.name = event.getName();
        dto.venue = event.getVenue();
        dto.date = event.getDate();
        dto.price = event.getPrice();
        dto.image = event.getImage();
        dto.availableSlots = event.getAvailableSlots().stream()
                .map(TimeSlotDTO::from)
                .collect(Collectors.toList());
        return dto;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getVenue() { return venue; }
    public LocalDateTime getDate() { return date; }
    public double getPrice() { return price; }
    public String getImage() { return image; }
    public List<TimeSlotDTO> getAvailableSlots() { return availableSlots; }
}
