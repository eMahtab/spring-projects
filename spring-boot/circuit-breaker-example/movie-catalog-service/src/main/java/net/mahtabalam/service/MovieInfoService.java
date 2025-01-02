package net.mahtabalam.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import net.mahtabalam.dto.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieInfoService {

    private final Logger logger = LoggerFactory.getLogger(MovieInfoService.class);
    private final RestTemplate restTemplate;

    public MovieInfoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @CircuitBreaker(name = "FETCH_MOVIE_INFO", fallbackMethod = "getMovieInfoFallback")
    public Movie getMovieInfo(String movieId) {
        return restTemplate.getForObject("http://localhost:8082/movies/" + movieId, Movie.class);
    }

    public Movie getMovieInfoFallback(String movieId, Throwable t) {
        logger.warn("getMovieInfoFallback triggered for movieId:{} with error:{}", movieId, t.getMessage());
        return new Movie("","","");
    }
}