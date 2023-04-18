package com.app.audienceize.services;

import com.app.audienceize.dtos.requests.ReviewRequest;
import com.app.audienceize.entities.Movie;
import com.app.audienceize.entities.Review;
import com.app.audienceize.repositories.MovieRepository;
import com.app.audienceize.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

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
            movie.setRatings(sum / reviews.size());
            movieRepository.save(movie);
            return "Your review has been posted for the movie"+ review.getMovieTitle();
        }
        throw new NoSuchElementException(request.getMovieTitle()+" is not currently available");
    }

    private Review toEntity(ReviewRequest request) {
        return Review.builder()
                .id(UUID.randomUUID().toString())
                .movieTitle(request.getMovieTitle())
                .rating(request.getRating())
                .comment(request.getComment())
                .build();
    }
}
