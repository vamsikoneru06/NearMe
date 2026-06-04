package com.nearme.dto;

import com.nearme.model.Review;

import java.time.LocalDateTime;

public class ReviewDTO {

    private Long id;
    private Long userId;
    private String userName;
    private Long locationId;
    private String locationName;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;

    public static ReviewDTO from(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.id = review.getId();
        dto.userId = review.getUser().getId();
        dto.userName = review.getUser().getName();
        dto.locationId = review.getLocation().getId();
        dto.locationName = review.getLocation().getName();
        dto.rating = review.getRating();
        dto.comment = review.getComment();
        dto.createdAt = review.getCreatedAt();
        return dto;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getUserName() { return userName; }
    public Long getLocationId() { return locationId; }
    public String getLocationName() { return locationName; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
