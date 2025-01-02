package net.mahtabalam.controller;

import net.mahtabalam.model.Review;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/movies")
public class MovieReviewController {

    @Value("${api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public MovieReviewController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RequestMapping("/{movieId}/reviews")
    public List<Review> getMovieInfo(@PathVariable("movieId") String movieId) {
        String url = "https://api.themoviedb.org/3/movie/" + movieId + "/reviews?api_key=" + apiKey;

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        if (response != null) {
            List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("results");
            return results.stream()
                          .map(this::mapToReview)
                          .toList();
        }
        return List.of();
    }

    private Review mapToReview(Map<String, Object> map) {
        return new Review(
                (String) map.get("id"),
                (String) map.get("author"),
                (String) map.get("content")
        );
    }
}
