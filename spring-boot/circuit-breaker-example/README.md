## Implementing Circuit Breaker in Spring Boot Microservices using Resilience4j

This demo is based on [Java Brains video](https://www.youtube.com/watch?v=o8RO38KbWvA&list=PLqq-6Pq4lTTbXZY_elyGv7IkKrfkSrX5e), with some modifications and it uses Resilience4j rather than Hysterix to implement Circuit breaker functionality. But the overall gist is same.


## All the microservices registered with Eureka server

This demo creates three microservices, [movie-catalog-service](https://github.com/eMahtab/spring-projects/blob/main/spring-boot/circuit-breaker-example/README.md#movie-catalog-service) calls the [movie-info-service](https://github.com/eMahtab/spring-projects/blob/main/spring-boot/circuit-breaker-example/README.md#movie-info-service) (to fetch the info for a movie) and [movie-review-service](https://github.com/eMahtab/spring-projects/blob/main/spring-boot/circuit-breaker-example/README.md#movie-review-service) (to fetch the reviews for a movie).

!["Microservices registered with Eureka server"](images/eureka-server.png?raw=true)


# Movie Info Service

!["Movie Info Service"](images/movie-info-service.png?raw=true)

# Movie Review Service

!["Movie Review Service"](images/movie-review-service.png?raw=true)

# Movie Catalog Service

!["Movie Catalog Service"](images/movie-catalog-service.png?raw=true)


# FETCH_MOVIE_INFO Circuit Breaker and fallback method
```java
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
```
# FETCH_MOVIE_REVIEWS Circuit Breaker and fallback method
```java
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
```
# Circuit Breakers Status :

!["Circuit Breakers Status"](images/circuit-breakers-status.png?raw=true)
