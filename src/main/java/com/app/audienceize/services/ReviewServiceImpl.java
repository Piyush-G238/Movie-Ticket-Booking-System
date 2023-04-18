package com.app.audienceize.services;

import com.app.audienceize.dtos.requests.ReviewRequest;
import com.app.audienceize.dtos.responses.ReviewResponse;
import com.app.audienceize.entities.Movie;
import com.app.audienceize.entities.Review;
import com.app.audienceize.repositories.MovieRepository;
import com.app.audienceize.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService{
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
        List<Review> newReviewList = null;
        if (reviews.size() <= 5){
            newReviewList = reviews;
        } else{
            newReviewList = reviews.subList(0, 6);
        }
        return newReviewList.stream()
                .map(this::toResponse)
                .sorted((r1, r2) -> (int) (r2.getRating() - r1.getRating()))
                .collect(Collectors.toList());
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
