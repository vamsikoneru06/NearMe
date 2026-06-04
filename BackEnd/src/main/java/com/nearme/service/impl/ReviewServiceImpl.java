package com.nearme.service.impl;

import com.nearme.dto.ReviewDTO;
import com.nearme.dto.ReviewRequest;
import com.nearme.exception.ResourceNotFoundException;
import com.nearme.exception.ValidationException;
import com.nearme.model.Location;
import com.nearme.model.Review;
import com.nearme.model.User;
import com.nearme.repository.LocationRepository;
import com.nearme.repository.ReviewRepository;
import com.nearme.repository.UserRepository;
import com.nearme.service.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository   reviewRepository;
    private final LocationRepository locationRepository;
    private final UserRepository     userRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository,
                             LocationRepository locationRepository,
                             UserRepository userRepository) {
        this.reviewRepository   = reviewRepository;
        this.locationRepository = locationRepository;
        this.userRepository     = userRepository;
    }

    // ── Add ───────────────────────────────────────────────────────────────────

    @Override
    public ReviewDTO addReview(Long locationId, ReviewRequest request, String userEmail) {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new ResourceNotFoundException("Location", locationId));

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userEmail));

        if (reviewRepository.existsByUserIdAndLocationId(user.getId(), locationId)) {
            throw new ValidationException("You have already reviewed this location");
        }

        Review review = new Review();
        review.setUser(user);
        review.setLocation(location);
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review = reviewRepository.save(review);

        recalculateRating(location);

        return ReviewDTO.from(review);
    }

    // ── List ──────────────────────────────────────────────────────────────────

    @Override
    @Transactional(readOnly = true)
    public Page<ReviewDTO> getReviews(Long locationId, Pageable pageable) {
        if (!locationRepository.existsById(locationId)) {
            throw new ResourceNotFoundException("Location", locationId);
        }
        return reviewRepository.findByLocationId(locationId, pageable)
                .map(ReviewDTO::from);
    }

    // ── Delete ────────────────────────────────────────────────────────────────

    @Override
    public void deleteReview(Long reviewId, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userEmail));

        Review review = reviewRepository.findByIdAndUserId(reviewId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Review not found or you are not the owner"));

        Long locationId = review.getLocation().getId();
        reviewRepository.delete(review);

        // Flush so the DELETE is committed before the AVG query runs
        reviewRepository.flush();

        Location location = locationRepository.findById(locationId).orElseThrow();
        recalculateRating(location);
    }

    // ── Private helpers ───────────────────────────────────────────────────────

    /**
     * Recomputes the location's stored rating as the mean of all its reviews,
     * rounded to one decimal place.  Resets to 0.0 if there are no reviews.
     */
    private void recalculateRating(Location location) {
        Double avg = reviewRepository.averageRatingByLocationId(location.getId());
        location.setRating(avg != null ? Math.round(avg * 10.0) / 10.0 : 0.0);
        locationRepository.save(location);
    }
}
