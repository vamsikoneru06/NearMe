package com.nearme.dto;

import com.nearme.model.Activity;

import java.util.List;
import java.util.stream.Collectors;

public class ActivityDTO {

    private Long id;
    private String name;
    private String place;
    private double cost;
    private double rating;
    private String image;
    private List<TimeSlotDTO> availableSlots;

    public static ActivityDTO from(Activity activity) {
        ActivityDTO dto = new ActivityDTO();
        dto.id = activity.getId();
        dto.name = activity.getName();
        dto.place = activity.getPlace();
        dto.cost = activity.getCost();
        dto.rating = activity.getRating();
        dto.image = activity.getImage();
        dto.availableSlots = activity.getAvailableSlots().stream()
                .map(TimeSlotDTO::from)
                .collect(Collectors.toList());
        return dto;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getPlace() { return place; }
    public double getCost() { return cost; }
    public double getRating() { return rating; }
    public String getImage() { return image; }
    public List<TimeSlotDTO> getAvailableSlots() { return availableSlots; }
}
