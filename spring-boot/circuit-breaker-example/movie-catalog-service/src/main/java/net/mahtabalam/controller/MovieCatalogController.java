package net.mahtabalam.controller;

import net.mahtabalam.dto.Catalog;
import net.mahtabalam.dto.Movie;
import net.mahtabalam.dto.Review;
import net.mahtabalam.model.MovieCatalog;
import net.mahtabalam.service.MovieInfoService;
import net.mahtabalam.service.MovieReviewService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {

    private final MovieInfoService movieInfoService;
    private final MovieReviewService movieReviewService;

    public MovieCatalogController(MovieInfoService movieInfoService,
                                  MovieReviewService movieReviewService) {
        this.movieInfoService = movieInfoService;
        this.movieReviewService = movieReviewService;
    }

    @RequestMapping("/{catalogId}")
    public Catalog getCatalog(@PathVariable("catalogId") String catalogId) {
        MovieCatalog movieCatalog = new MovieCatalog();
        movieCatalog.setName("Most Popular Movies");
        movieCatalog.setDescription("A list of Most popular movies of all time");
        movieCatalog.setMovieIds(Arrays.asList("1495", "1496"));

        Catalog catalog = new Catalog();
        catalog.setName(movieCatalog.getName());
        catalog.setDescription(movieCatalog.getDescription());

        List<Movie> movies = movieCatalog.getMovieIds().stream()
                .map(movieId -> {
                    Movie movie = movieInfoService.getMovieInfo(movieId);
                    List<Review> reviews = movieReviewService.getMovieReviews(movieId);
                    movie.setReviews(reviews);
                    return movie;
                })
                .collect(Collectors.toList());

        catalog.setMovies(movies);
        return catalog;
    }
}
