package net.mahtabalam.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import net.mahtabalam.dto.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@Service
public class MovieReviewService {

    private final Logger logger = LoggerFactory.getLogger(MovieReviewService.class);

    private final RestTemplate restTemplate;

    public MovieReviewService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @CircuitBreaker(name = "FETCH_MOVIE_REVIEWS", fallbackMethod = "getMovieReviewsFallback")
    public List<Review> getMovieReviews(String movieId) {
        String url = "http://localhost:8083/movies/" + movieId + "/reviews";
        Review[] reviews = restTemplate.getForObject(url, Review[].class);
        return reviews != null ? Arrays.asList(reviews) : List.of();
    }

    public List<Review> getMovieReviewsFallback(String movieId, Throwable t) {
        logger.warn("getMovieReviewsFallback triggered for movieId:{} with error:{}", movieId, t.getMessage());
        return List.of();
    }
}