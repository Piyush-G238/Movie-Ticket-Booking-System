package com.app.audienceize.services.impl;

import com.app.audienceize.dtos.requests.ReviewRequest;
import com.app.audienceize.dtos.responses.ReviewResponse;
import com.app.audienceize.entities.Movie;
import com.app.audienceize.entities.Review;
import com.app.audienceize.repositories.MovieRepository;
import com.app.audienceize.repositories.ReviewRepository;
import com.app.audienceize.services.interfaces.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public String addReview(ReviewRequest request) {
        if (movieRepository.existsByTitle(request.getMovieTitle())){
            Review review = toEntity(request);
            Movie movie = movieRepository.findByTitle(review.getMovieTitle()).orElseThrow(() -> new NoSuchElementException("Movie with given title currently not available"));
            movie.addReview(review);
            review.setMovie(movie);

            List<Review> reviews = movie.getReviews();
            Double sum = 0.0;
            for (Review r: reviews) {
                sum += r.getRating();
            }
            movie.setRatings((double) Math.round((sum/reviews.size())* 100)/100.0);
            movieRepository.save(movie);
            return "Your review has been posted for the movie "+ review.getMovieTitle();
        }
        throw new NoSuchElementException(request.getMovieTitle()+" is not currently available");
    }

    @Override
    public List<ReviewResponse> getTop5ReviewsByMovieTitle(String movieTitle) {
        List<Review> reviews = reviewRepository.findByMovieTitle(movieTitle);
        if (reviews.size() == 0) {
            throw new NoSuchElementException("Reviews are not available for the given movie title");
        }
        reviews.sort((review1, review2) -> {
            return (int) (review2.getRating() - review1.getRating());
        });
        List<ReviewResponse> reviewResponses = reviews.stream().map(this::toResponse).toList();
        if (reviewResponses.size() <= 5){
            return reviewResponses;
        }
       return reviewResponses.subList(0, 6);
    }

    @Override
    public String updateReview(ReviewRequest request, String reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow();
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        reviewRepository.save(review);

        Movie movie = movieRepository.findByTitle(request.getMovieTitle()).orElseThrow();
        List<Review> reviews = movie.getReviews();
        Double sum = 0.0;
        for (Review rev: reviews) {
            sum += rev.getRating();
        }
        movie.setRatings((double) Math.round((sum/ reviews.size())*100) / 100);
        movieRepository.save(movie);
        return "Hey, your review has been updated";
    }

    @Override
    public String deleteReview(String reviewId) {
        if (reviewRepository.existsById(reviewId)) {
            reviewRepository.deleteById(reviewId);
            return "Hey, your review has been removed.";
        }
        else
            throw new NoSuchElementException("This review is not available");
    }

    private Review toEntity(ReviewRequest request) {
        return Review.builder()
                .id(UUID.randomUUID().toString())
                .movieTitle(request.getMovieTitle())
                .rating(request.getRating())
                .comment(request.getComment())
                .build();
    }

    private ReviewResponse toResponse(Review review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .movieTitle(review.getMovieTitle())
                .rating(review.getRating())
                .comment(review.getComment())
                .postedDate(review.getPostedOn())
                .build();
    }
}
