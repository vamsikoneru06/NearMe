package com.nearme.service;

import com.nearme.dto.ReviewDTO;
import com.nearme.dto.ReviewRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {

    /** Add a review to a location. Recalculates the location's average rating. */
    ReviewDTO addReview(Long locationId, ReviewRequest request, String userEmail);

    /** Paginated reviews for a location. */
    Page<ReviewDTO> getReviews(Long locationId, Pageable pageable);

    /** Delete a review — only the owner may delete their own review. */
    void deleteReview(Long reviewId, String userEmail);
}
